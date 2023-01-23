package com.example.gadgetariumb7.converter;

import com.example.gadgetariumb7.db.entity.Subscription;
import com.example.gadgetariumb7.dto.request.SubscriptionRequest;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionRequestConverter {
    public Subscription createSubscription(SubscriptionRequest subscriptionRequest) {
        if (subscriptionRequest == null) {
            return null;
        }
        Subscription subscription = new Subscription();
        subscription.setEmail(subscriptionRequest.getEmail());
        return subscription;
    }

}
