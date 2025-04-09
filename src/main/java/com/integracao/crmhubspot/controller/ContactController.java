package com.integracao.crmhubspot.controller;

import com.integracao.crmhubspot.dto.ContactRequest;
import com.integracao.crmhubspot.dto.ContactResponse;
import com.integracao.crmhubspot.dto.ListContactsResponse;
import com.integracao.crmhubspot.service.ContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/contact")
public class ContactController {

    private final ContactService contactService;

    @PostMapping
    public ResponseEntity<Object> createContact(@RequestBody @Valid ContactRequest contactRequest,
                                                         @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(contactService.createContact(contactRequest, token));
    }

    @GetMapping
    public ResponseEntity<ListContactsResponse> listContacts(@RequestParam(value = "limit", required = false) Integer limit,
                                                             @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(contactService.listContacts(limit, token));
    }

    @GetMapping("{contactId}")
    public ResponseEntity<ContactResponse> getContact(@PathVariable String contactId,
                                                      @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(contactService.getContact(contactId, token));
    }
}
