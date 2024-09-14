package com.home.ethfetcher.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthRequestModel {

    @NotNull
    private String username;

    @NotNull
    private String password;

}
