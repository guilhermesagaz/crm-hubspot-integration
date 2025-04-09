package com.integracao.crmhubspot.core.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class Constants {

    @Getter
    @AllArgsConstructor
    public enum ChaveMensagem {
        NOT_FOUND("not.found"),
        INVALID_ID("invalid.id"),
        BAD_REQUEST("bad.request"),
        PARAMETRO_INVALIDO("parametro.invalido"),
        REQUISICAO_INVALIDA("requisicao.invalida"),
        RATE_MIMITER_EXCEEDED("rate-limiter.exceeded"),
        HUBSPOT_CREDENTIALS_INVALID("hubspot.credentials.invalid"),
        HUBSPOT_CREDENTIALS_FORBIDDEN("hubspot.credentials.forbidden"),
        HUBSPOT_API_EXCEPTION("hubspot.api.exception"),
        FEIGN_SERVICE_UNAVAILABLE("feign.service.unavailable"),
        FEIGN_SERVICE_GATEWAY_TIMEOUT("feign.service.gateway-timeout"),
        FEIGN_ENTITY_NOT_FOUND("feign.entity.not-found");

        private final String descricao;
    }

    @Getter
    @AllArgsConstructor
    public enum Auth {
        AUTHORIZE_URL("https://app.hubspot.com/oauth/authorize"),
        AUTHORIZATION_CODE("authorization_code"),
        REFRESH_TOKEN("refresh_token"),
        BEARER("Bearer ");

        private final String descricao;
    }
}
