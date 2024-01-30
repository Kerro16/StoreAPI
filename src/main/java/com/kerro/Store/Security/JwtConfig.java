package com.kerro.Store.Security;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.Key;

@Configuration
public class JwtConfig {



    @Bean
    public Key key(){
        return Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

}
