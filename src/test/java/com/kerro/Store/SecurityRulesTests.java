package com.kerro.Store;

import com.kerro.Store.model.ERole;
import com.kerro.Store.model.Product;
import com.kerro.Store.model.Role;
import com.kerro.Store.repository.ProductRepository;
import com.kerro.Store.repository.RoleRepository;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** Access rules: sign-up roles, product/user routes, validation and the JWT cookie flow. */
@SpringBootTest
@AutoConfigureMockMvc
class SecurityRulesTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void seedRoles() {
        for (ERole role : ERole.values()) {
            if (roleRepository.findByName(role).isEmpty()) {
                roleRepository.save(new Role(role));
            }
        }
    }

    private static String unique(String prefix) {
        return prefix + UUID.randomUUID().toString().substring(0, 8);
    }

    private static String signupJson(String name, String password, String roleJson) {
        return "{\"username\":\"" + name + "\",\"email\":\"" + name + "@example.com\",\"password\":\"" + password + "\""
                + (roleJson == null ? "" : ",\"role\":" + roleJson) + "}";
    }

    private static String loginJson(String name, String password) {
        return "{\"username\":\"" + name + "\",\"password\":\"" + password + "\"}";
    }

    @Test
    void signupIgnoresRequestedAdminRole() throws Exception {
        String name = unique("evil");
        mvc.perform(post("/api/auth/signup").contentType(MediaType.APPLICATION_JSON)
                        .content(signupJson(name, "password123", "[\"admin\",\"mod\"]")))
                .andExpect(status().isOk());

        mvc.perform(post("/api/auth/signin").contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson(name, "password123")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roles", hasSize(1)))
                .andExpect(jsonPath("$.roles[0]").value("ROLE_USER"));
    }

    @Test
    void signupRejectsShortPasswordAndBadEmail() throws Exception {
        String name = unique("short");
        mvc.perform(post("/api/auth/signup").contentType(MediaType.APPLICATION_JSON)
                        .content(signupJson(name, "abc", null)))
                .andExpect(status().isBadRequest());

        mvc.perform(post("/api/auth/signup").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"" + name + "\",\"email\":\"not-an-email\",\"password\":\"password123\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void signinSetsHttpOnlySameSiteCookieThatAuthenticatesLaterRequests() throws Exception {
        String name = unique("cookie");
        mvc.perform(post("/api/auth/signup").contentType(MediaType.APPLICATION_JSON)
                        .content(signupJson(name, "password123", null)))
                .andExpect(status().isOk());

        MvcResult login = mvc.perform(post("/api/auth/signin").contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson(name, "password123")))
                .andExpect(status().isOk())
                .andExpect(header().string("Set-Cookie", containsString("HttpOnly")))
                .andExpect(header().string("Set-Cookie", containsString("SameSite=Lax")))
                .andReturn();

        Cookie jwt = login.getResponse().getCookie("store_cookie");
        mvc.perform(get("/api/auth/validateuser").cookie(jwt)).andExpect(status().isOk());
        mvc.perform(get("/api/auth/validateuser")).andExpect(status().isUnauthorized());
    }

    @Test
    void anonymousCanListProductsButNotChangeThem() throws Exception {
        mvc.perform(get("/api/product/getall")).andExpect(status().isOk());

        mvc.perform(post("/api/product/add").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"" + unique("p") + "\",\"price\":\"10\",\"stock\":1}"))
                .andExpect(status().isUnauthorized());
        mvc.perform(post("/api/product/update").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"name\":\"x\",\"price\":\"10\",\"stock\":1}"))
                .andExpect(status().isUnauthorized());
        mvc.perform(post("/api/product/delete").param("id", "1")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "USER")
    void regularUserCannotChangeProducts() throws Exception {
        mvc.perform(post("/api/product/add").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"" + unique("p") + "\",\"price\":\"10\",\"stock\":1}"))
                .andExpect(status().isForbidden());
        mvc.perform(post("/api/product/delete").param("id", "1")).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void adminCanAddAndReallyDeleteProducts() throws Exception {
        String name = unique("prod");
        mvc.perform(post("/api/product/add").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"" + name + "\",\"price\":\"10\",\"stock\":1}"))
                .andExpect(status().isOk());

        Product saved = productRepository.findByName(name).orElseThrow();
        mvc.perform(delete("/api/product/delete").param("id", String.valueOf(saved.getId())))
                .andExpect(status().isOk());
        org.junit.jupiter.api.Assertions.assertTrue(productRepository.findById(saved.getId()).isEmpty());

        mvc.perform(delete("/api/product/delete").param("id", String.valueOf(saved.getId())))
                .andExpect(status().isNotFound());
    }

    @Test
    void userListRequiresAdminAndNeverExposesPasswords() throws Exception {
        String name = unique("listed");
        mvc.perform(post("/api/auth/signup").contentType(MediaType.APPLICATION_JSON)
                .content(signupJson(name, "password123", null))).andExpect(status().isOk());

        mvc.perform(get("/api/users/getall")).andExpect(status().isUnauthorized());
        mvc.perform(get("/api/users/getall").with(org.springframework.security.test.web.servlet.request
                        .SecurityMockMvcRequestPostProcessors.user("someone").roles("USER")))
                .andExpect(status().isForbidden());
        mvc.perform(get("/api/users/getall").with(org.springframework.security.test.web.servlet.request
                        .SecurityMockMvcRequestPostProcessors.user("boss").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(name)))
                .andExpect(content().string(not(containsString("password"))))
                .andExpect(content().string(not(containsString("$2a$"))));
    }
}
