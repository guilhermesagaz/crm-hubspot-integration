package com.integracao.crmhubspot.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class ListContactsResponse {

    private List<ContactResponse> results;
}
