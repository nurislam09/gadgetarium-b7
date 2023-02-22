package com.example.gadgetariumb7.db.service.impl;

import com.example.gadgetariumb7.db.entity.Subscription;
import com.example.gadgetariumb7.db.repository.SubscriptionRepository;
import com.example.gadgetariumb7.db.service.SubscriptionService;
import com.example.gadgetariumb7.dto.request.SubscriptionRequest;
import com.example.gadgetariumb7.exceptions.EmailAlreadyExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    @Override
    public ResponseEntity<String> save(SubscriptionRequest subscriptionRequest) {
        Subscription subscription = new Subscription();
        subscription.setEmail(subscriptionRequest.getEmail());
        if (subscriptionRepository.existsByEmail(subscription.getEmail())) {
            throw new EmailAlreadyExistException("Email already registered");
        }
        subscriptionRepository.save(subscription);
        return new ResponseEntity<>("Successfully subscribed", HttpStatus.CREATED);
    }

    @Override
    public List<Subscription> findAll() {
        return subscriptionRepository.findAll();
    }
}
