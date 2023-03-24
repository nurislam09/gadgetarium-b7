package com.example.gadgetariumb7.db.service;

import com.example.gadgetariumb7.db.entity.Mailing;
import com.example.gadgetariumb7.db.entity.Order;
import com.example.gadgetariumb7.db.entity.Subscription;
import com.example.gadgetariumb7.db.repository.UserRepository;
import com.example.gadgetariumb7.dto.request.MailingRequest;
import com.example.gadgetariumb7.dto.response.SimpleResponse;

public interface MailingService {

    void saveMailing(Mailing mailing);

    SimpleResponse sendMailing(MailingRequest mailingRequest);

    void sendEmail(String email, Mailing mailing);

    void sendEmail(String email, Order order);
}
