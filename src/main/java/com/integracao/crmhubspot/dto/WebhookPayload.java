package com.integracao.crmhubspot.dto;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class WebhookPayload {

    private Long appId;
    private Long eventId;
    private Long subscriptionId;
    private Long portalId;
    private Long ocurredAt;
    private String subscriptionType;
    private Integer attemptNumber;
    private Long objectId;
    private String changeSource;
    private String changeFlag;
}
