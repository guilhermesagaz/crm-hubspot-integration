package com.integracao.crmhubspot.feign.client;

import com.integracao.crmhubspot.config.FeignConfig;
import com.integracao.crmhubspot.dto.ContactRequest;
import com.integracao.crmhubspot.dto.ContactResponse;
import com.integracao.crmhubspot.dto.HubSpotTokenResponse;
import com.integracao.crmhubspot.dto.ListContactsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "hubspot-api", url = "${hubspot.api.url}", configuration = FeignConfig.class)
public interface HubSpotApiClient {

    @PostMapping(value = "oauth/v1/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    HubSpotTokenResponse exchangeCodeForToken(@RequestParam("grant_type") String grantType,
                                              @RequestParam("client_id") String clientId,
                                              @RequestParam("client_secret") String clientSecret,
                                              @RequestParam("redirect_uri") String redirectUri,
                                              @RequestParam("code") String code);

    @PostMapping(value = "oauth/v1/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    HubSpotTokenResponse refreshToken(@RequestParam("grant_type") String grantType,
                                      @RequestParam("client_id") String clientId,
                                      @RequestParam("client_secret") String clientSecret,
                                      @RequestParam("redirect_uri") String redirectUri,
                                      @RequestParam("refresh_token") String refreshToken);

    @PostMapping(value = "crm/v3/objects/contacts", consumes = MediaType.APPLICATION_JSON_VALUE)
    Object createContact(@RequestBody ContactRequest contactRequest,
                         @RequestHeader("Authorization") String authorization);

    @GetMapping("crm/v3/objects/contacts/{contactId}")
    ContactResponse getContact(@PathVariable("contactId") String contactId,
                               @RequestHeader("Authorization") String authorization);

    @GetMapping("crm/v3/objects/contacts")
    ListContactsResponse listContacts(@RequestParam(value = "limit") Integer limit,
                                      @RequestHeader("Authorization") String authorization);
}
