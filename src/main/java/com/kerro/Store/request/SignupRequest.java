package com.kerro.Store.request;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

@Setter
@Getter
public class SignupRequest {

    @NotBlank
    @Size(max = 255)
    private String username;

    @NotBlank
    @Email
    @Size(max = 255)
    private String email;

    private Set<String> role;

    @NotBlank
    @Size(min = 8, max = 128)
    private String password;
}
