package com.example.gadgetariumb7.db.service.impl;

import com.example.gadgetariumb7.db.service.PaymentService;
import com.example.gadgetariumb7.dto.request.PaymentRequest;
import com.example.gadgetariumb7.dto.response.SimpleResponse;
import com.example.gadgetariumb7.exceptions.BadRequestException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {
    @Value("${stripe.apiKey}")
    private String apiKey;

    @Override
    public SimpleResponse chargeCreditCard(PaymentRequest paymentRequest) {
        try{
        Stripe.apiKey = apiKey;
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", paymentRequest.getAmount());
        chargeParams.put("currency", paymentRequest.getCurrency());
        chargeParams.put("source", paymentRequest.getToken());
        Charge.create(chargeParams);
        log.info("successfully the charge credit card method works ");
        return new SimpleResponse("Payment successful!", "200");}
        catch (StripeException e){
            log.error("Something wrong with token");
            return new SimpleResponse(e.getMessage(), "404");
        }
    }

}

