package com.integracao.crmhubspot.service;

import com.integracao.crmhubspot.dto.ListContactsResponse;
import com.integracao.crmhubspot.feign.client.HubSpotApiClient;
import com.integracao.crmhubspot.dto.ContactRequest;
import com.integracao.crmhubspot.dto.ContactResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ContactService {

    private final HubSpotApiClient hubSpotApiClient;

    public Object createContact(ContactRequest request, String token) {
        return hubSpotApiClient.createContact(request, token);
    }

    public ListContactsResponse listContacts(Integer limit, String token) {
        return hubSpotApiClient.listContacts(limit, token);
    }

    public ContactResponse getContact(String contactId, String token) {
        return hubSpotApiClient.getContact(contactId, token);
    }
}
