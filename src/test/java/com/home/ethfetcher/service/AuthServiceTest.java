package com.home.ethfetcher.service;

import com.home.ethfetcher.model.AuthRequestModel;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.mockito.ArgumentMatchers.any;

public class AuthServiceTest {

    @Test
    void authenticate() {
        AuthenticationManager authenticationManager = Mockito.mock(AuthenticationManager.class);
        JwtService jwtService = Mockito.mock(JwtService.class);

        AuthService authService = new AuthService(authenticationManager, jwtService);

        AuthRequestModel requestModel = new AuthRequestModel();
        requestModel.setUsername("username");
        requestModel.setPassword("password");

        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);

        authService.authenticate(requestModel);

        Mockito.verify(authenticationManager, Mockito.times(1)).authenticate(authentication);
    }
}
