package com.kerro.store.request;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

@Getter
@Setter
public class UserUpdateRequest {

    @NotNull
    private Long id;

    @NotNull
    private String username;

    @NotNull
    private String email;

    private Set<String> role;

    @NotNull
    private String password;

}
