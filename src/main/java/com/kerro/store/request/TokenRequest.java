package com.kerro.store.request;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Setter
@Getter
public class TokenRequest {

    @NotNull
    private String token;

}
