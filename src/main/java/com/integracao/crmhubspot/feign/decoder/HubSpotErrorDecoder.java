package com.integracao.crmhubspot.feign.decoder;

import com.integracao.crmhubspot.exception.HubspotApiException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

import static com.integracao.crmhubspot.core.constants.Constants.ChaveMensagem.*;

public class HubSpotErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {
        return switch (response.status()) {
            case 400 -> new HubspotApiException(BAD_REQUEST.getDescricao(), HttpStatus.BAD_REQUEST);
            case 401 -> new HubspotApiException(HUBSPOT_CREDENTIALS_INVALID.getDescricao(), HttpStatus.UNAUTHORIZED);
            case 403 -> new HubspotApiException(HUBSPOT_CREDENTIALS_FORBIDDEN.getDescricao(), HttpStatus.FORBIDDEN);
            case 404 -> new HubspotApiException(NOT_FOUND.getDescricao(), HttpStatus.NOT_FOUND);
            case 429 -> new HubspotApiException(RATE_MIMITER_EXCEEDED.getDescricao(), HttpStatus.TOO_MANY_REQUESTS);
            default -> new HubspotApiException(HUBSPOT_API_EXCEPTION.getDescricao(), HttpStatus.INTERNAL_SERVER_ERROR);
        };
    }
}
