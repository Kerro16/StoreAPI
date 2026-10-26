package com.kerro.Store.request;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

@Setter
@Getter
public class UserCreationRequest {

    @NotNull
    private String username;

    @NotNull
    private String email;

    private Set<String> role;

    @NotNull
    private String password;

}
