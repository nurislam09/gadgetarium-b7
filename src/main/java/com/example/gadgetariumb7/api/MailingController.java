package com.example.gadgetariumb7.api;

import com.example.gadgetariumb7.db.entity.Mailing;
import com.example.gadgetariumb7.db.service.MailingService;
import com.example.gadgetariumb7.db.service.SubscriptionService;
import com.example.gadgetariumb7.dto.request.MailingRequest;
import com.example.gadgetariumb7.dto.request.SubscriptionRequest;
import com.example.gadgetariumb7.exceptions.EmailAlreadyExistException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mailings")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "mailing api")
public class MailingController {
    private final MailingService emailService;
    private final SubscriptionService subscriptionService;

    @Operation(summary = "subscribe ", description = "Any user can subscribe for mailing and he will be saved in our database in subscription table, we need only email")
    @PostMapping("/subscribe")
    public ResponseEntity<String> subscribe(@RequestBody SubscriptionRequest subscriptionRequest) throws EmailAlreadyExistException {
        return subscriptionService.save(subscriptionRequest);
    }

    @Operation(summary = "send mailing", description = "This endpoint sends the mailing for all emails which saved in our database in subscriptions table ")
    @PostMapping("/message")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Mailing> sendEmail(@RequestBody MailingRequest mailingRequest) {
        emailService.sendMailing(mailingRequest);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
