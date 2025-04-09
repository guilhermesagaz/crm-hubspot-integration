package com.integracao.crmhubspot.service;

import com.integracao.crmhubspot.dto.WebhookPayload;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WebhookService {

    public void processContactCreation(WebhookPayload payload, String signature) {
        log.info("Processando evendo de criação de contato: {}", payload.toString());
    }
}
