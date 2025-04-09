package com.integracao.crmhubspot.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactRequest {

    private Properties properties;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Properties {
        private String email;
        private String firstname;
        private String lastname;
        private String phone;
    }
}
