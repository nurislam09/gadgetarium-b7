package com.example.gadgetariumb7.db.service;

import com.example.gadgetariumb7.db.entity.Subscription;
import com.example.gadgetariumb7.dto.request.SubscriptionRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SubscriptionService {

    ResponseEntity<String> save(SubscriptionRequest subscriptionRequest);

    List<Subscription> findAll();

}
