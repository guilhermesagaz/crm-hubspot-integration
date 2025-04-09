package com.integracao.crmhubspot.config;

import com.integracao.crmhubspot.core.util.HubSpotRateLimiter;
import com.integracao.crmhubspot.exception.HubspotApiException;
import com.integracao.crmhubspot.feign.decoder.HubSpotErrorDecoder;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import static com.integracao.crmhubspot.core.constants.Constants.ChaveMensagem.RATE_MIMITER_EXCEEDED;

@Configuration
public class FeignConfig {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new HubSpotErrorDecoder();
    }

    @Bean
    public RequestInterceptor rateLimitingInterceptor(HubSpotRateLimiter rateLimiter) {
        return request -> {
            if (request.url().contains("/contact")) {
                if (!rateLimiter.tryAcquire()) {
                    throw new HubspotApiException(RATE_MIMITER_EXCEEDED.getDescricao(), HttpStatus.TOO_MANY_REQUESTS);
                }
            }
        };
    }
}
