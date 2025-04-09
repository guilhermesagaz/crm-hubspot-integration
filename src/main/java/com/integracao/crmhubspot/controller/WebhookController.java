package com.integracao.crmhubspot.controller;

import com.integracao.crmhubspot.dto.WebhookPayload;
import com.integracao.crmhubspot.service.WebhookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/webhook")
public class WebhookController {

    private final WebhookService webhookService;

    @PostMapping("/contact-creation")
    public ResponseEntity<Void> handleContactCreation(@RequestBody List<WebhookPayload> payload,
                                                      @RequestHeader("X-HubSpot-Signature") String signature) {
        webhookService.processContactCreation(payload.getFirst(), signature);

        return ResponseEntity.noContent().build();
    }
}
