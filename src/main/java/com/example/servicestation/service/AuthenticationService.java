package com.example.servicestation.service;

import com.example.servicestation.security.JWTService;
import com.example.servicestation.service.dto.AppUserDTO;
import com.example.servicestation.service.request.AuthenticationRequest;
import com.example.servicestation.service.response.AuthenticationResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JWTService jwtService;
    private final AppUserService appUserService;
    private final AuthenticationManager authenticationManager;


    public AuthenticationResponse login(AuthenticationRequest authRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authRequest.getLogin(),
                authRequest.getPassword()));
        return new AuthenticationResponse(jwtService.generateToken(authRequest.getLogin()));
    }

    @Transactional
    public AppUserDTO signUp(AppUserDTO dto) {
        return appUserService.save(dto);
    }

}
