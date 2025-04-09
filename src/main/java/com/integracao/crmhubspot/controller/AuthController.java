package com.integracao.crmhubspot.controller;

import com.integracao.crmhubspot.dto.HubSpotTokenResponse;
import com.integracao.crmhubspot.factory.AuthTokenFatory;
import com.integracao.crmhubspot.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/auth")
public class AuthController {

    private final AuthService service;
    private final AuthTokenFatory fatory;

    @GetMapping("authorize-url")
    public ResponseEntity<String> getAuthorizationUrl() {
        return ResponseEntity.ok(service.generateAuthorizationUrl());
    }

    @GetMapping("callback")
    public ResponseEntity<HubSpotTokenResponse> handleCallback(@RequestParam String code) {
        return ResponseEntity.ok(service.exchangeCodeForToken(code));
    }

    @GetMapping("refresh-token")
    public ResponseEntity<HubSpotTokenResponse> refreshToken(@RequestParam String refreshToken) {
        return ResponseEntity.ok(service.refreshToken(refreshToken));
    }

    @GetMapping("last-token")
    public ResponseEntity<HubSpotTokenResponse> getLastToken() {
        return ResponseEntity.ok(fatory.toResponte(service.getLastToken()));
    }
}
