package com.example.gadgetariumb7.db.service;

import com.example.gadgetariumb7.converter.SubscriptionRequestConverter;
import com.example.gadgetariumb7.db.entity.Subscription;
import com.example.gadgetariumb7.db.repository.SubscriptionRepository;
import com.example.gadgetariumb7.dto.request.SubscriptionRequest;
import com.example.gadgetariumb7.mailing.EmailAlreadyExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionRequestConverter subscriptionRequestConverter;

    public void save(SubscriptionRequest subscriptionRequest) {
        Subscription subscription = subscriptionRequestConverter.createSubscription(subscriptionRequest);
        if(subscriptionRepository.existsByEmail(subscription.getEmail())){
            throw new EmailAlreadyExistException("Email already registered");
        }
        subscriptionRepository.save(subscription);
    }

    public List<Subscription> findAll() {
        return subscriptionRepository.findAll();
    }
}
