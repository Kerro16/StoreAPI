package com.kerro.Store.request;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;

@Setter
@Getter
public class LoginRequest {

    @NotNull
    private String username;

    @NotNull
    private String password;


}