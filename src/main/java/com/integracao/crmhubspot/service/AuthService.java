package com.integracao.crmhubspot.service;

import com.integracao.crmhubspot.core.message.MessageHandler;
import com.integracao.crmhubspot.exception.NotFoundException;
import com.integracao.crmhubspot.factory.AuthTokenFatory;
import com.integracao.crmhubspot.feign.client.HubSpotApiClient;
import com.integracao.crmhubspot.dto.HubSpotTokenResponse;
import com.integracao.crmhubspot.model.AuthToken;
import com.integracao.crmhubspot.repository.AuthTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import static com.integracao.crmhubspot.core.constants.Constants.Auth.*;
import static com.integracao.crmhubspot.core.constants.Constants.ChaveMensagem.NOT_FOUND;

@RequiredArgsConstructor
@Service
public class AuthService {

    @Value("${hubspot.client.id}")
    private String clientId;

    @Value("${hubspot.client.secret}")
    private String clientSecret;

    @Value("${hubspot.redirect.uri}")
    private String redirectUri;

    @Value("${hubspot.scopes}")
    private String scopes;

    private final AuthTokenRepository repository;
    private final AuthTokenFatory fatory;
    private final HubSpotApiClient hubSpotApiClient;
    private final MessageHandler messageHandler;

    public String generateAuthorizationUrl() {
        return UriComponentsBuilder
                .fromHttpUrl(AUTHORIZE_URL.getDescricao())
                .queryParam("client_id", clientId)
                .queryParam("scope", scopes)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("response_type", "code")
                .toUriString();
    }

    public HubSpotTokenResponse exchangeCodeForToken(String code) {
        HubSpotTokenResponse tokenResponse = hubSpotApiClient.exchangeCodeForToken(
                AUTHORIZATION_CODE.getDescricao(),
                clientId,
                clientSecret,
                redirectUri,
                code);

        saveToken(tokenResponse);

        return tokenResponse;
    }

    public HubSpotTokenResponse refreshToken(String refreshToken) {
        HubSpotTokenResponse tokenResponse = hubSpotApiClient.refreshToken(
                REFRESH_TOKEN.getDescricao(),
                clientId,
                clientSecret,
                redirectUri,
                refreshToken);

        saveToken(tokenResponse);

        return tokenResponse;
    }

    public AuthToken getLastToken() {
        return repository.findFirstByOrderByIdDesc()
                .orElseThrow(() -> new NotFoundException(messageHandler.getMessage(NOT_FOUND.getDescricao())));
    }

    private void saveToken(HubSpotTokenResponse tokenResponse) {
        repository.findByAccessToken(tokenResponse.getAccessToken())
                .ifPresent(repository::delete);

        repository.save(fatory.toEntity(tokenResponse));
    }
}
