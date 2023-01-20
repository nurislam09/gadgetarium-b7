package com.example.gadgetariumb7.api;

import com.example.gadgetariumb7.db.entity.Mailing;
import com.example.gadgetariumb7.db.service.MailingService;
import com.example.gadgetariumb7.db.service.SubscriptionService;
import com.example.gadgetariumb7.dto.request.MailingRequest;
import com.example.gadgetariumb7.dto.request.SubscriptionRequest;
import com.example.gadgetariumb7.exceptions.EmailAlreadyExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mailings")
public class MailingController {
    private final MailingService emailService;
    private final SubscriptionService subscriptionService;

    @PostMapping("/subscribe")
    public ResponseEntity<String> subscribe(@RequestBody SubscriptionRequest subscriptionRequest) {
        try {
            subscriptionService.save(subscriptionRequest);
        } catch (EmailAlreadyExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>("Successfully subscribed", HttpStatus.CREATED);
    }

    @PostMapping("/message")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Mailing> sendEmail(@RequestBody MailingRequest mailingRequest) {
        emailService.sendMailing(mailingRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
