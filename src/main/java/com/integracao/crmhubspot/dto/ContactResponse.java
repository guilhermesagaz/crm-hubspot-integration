package com.integracao.crmhubspot.dto;

import lombok.*;

@Setter
@Getter
@Builder
public class ContactResponse {

    private String id;
    private Properties properties;
    private String createdAt;
    private String updatedAt;
    private Boolean archived;

    @Getter
    @Setter
    @Builder
    public static class Properties {
        private String createdate;
        private String email;
        private String firstname;
        private String lastname;
        private String lastmodifieddate;
    }
}
