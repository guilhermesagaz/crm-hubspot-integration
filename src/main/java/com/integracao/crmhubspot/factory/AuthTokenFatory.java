package com.integracao.crmhubspot.factory;

import com.integracao.crmhubspot.dto.HubSpotTokenResponse;
import com.integracao.crmhubspot.model.AuthToken;
import org.springframework.stereotype.Component;

@Component
public class AuthTokenFatory {

    public AuthToken toEntity(HubSpotTokenResponse response) {
        return AuthToken.builder()
                .accessToken(response.getAccessToken())
                .refreshToken(response.getRefreshToken())
                .expiresIn(response.getExpiresIn())
                .tokenType(response.getTokenType())
                .build();
    }

    public HubSpotTokenResponse toResponte(AuthToken entity) {
        return HubSpotTokenResponse.builder()
                .accessToken(entity.getAccessToken())
                .refreshToken(entity.getRefreshToken())
                .expiresIn(entity.getExpiresIn())
                .tokenType(entity.getTokenType())
                .build();
    }
}
