package com.example.gadgetariumb7.db.service.impl;

import com.example.gadgetariumb7.db.entity.Subscription;
import com.example.gadgetariumb7.db.repository.SubscriptionRepository;
import com.example.gadgetariumb7.db.service.SubscriptionService;
import com.example.gadgetariumb7.dto.request.SubscriptionRequest;
import com.example.gadgetariumb7.dto.response.SimpleResponse;
import com.example.gadgetariumb7.exceptions.EmailAlreadyExistException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    @Override
    public SimpleResponse save(SubscriptionRequest subscriptionRequest) {
        Subscription subscription = new Subscription();
        subscription.setEmail(subscriptionRequest.getEmail());
        if (subscriptionRepository.existsByEmail(subscription.getEmail())) {
            throw new EmailAlreadyExistException("Email already registered");
        }
        subscriptionRepository.save(subscription);
        log.info("successfully works the save method");
        return new SimpleResponse("Successfully subscribed", "ok");
    }

    @Override
    public List<Subscription> findAll() {
        log.info("successfully works the find all subscriptions method");
        return subscriptionRepository.findAll();
    }
}
