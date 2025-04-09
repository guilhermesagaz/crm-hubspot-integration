package com.integracao.crmhubspot.core.util;

import com.google.common.util.concurrent.RateLimiter;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class HubSpotRateLimiter {

    private RateLimiter rateLimiter;

    // HubSpot permite 110 requests por 10 segundos (11 requests/segundo)
    private static final double REQUESTS_PER_SECOND = 11.0;

    @PostConstruct
    public void init() {
        this.rateLimiter = RateLimiter.create(REQUESTS_PER_SECOND, 10, TimeUnit.SECONDS);
    }

    public boolean tryAcquire() {
        return rateLimiter.tryAcquire();
    }
}
