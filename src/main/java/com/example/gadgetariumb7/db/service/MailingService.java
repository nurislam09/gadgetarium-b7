package com.example.gadgetariumb7.db.service;

import com.example.gadgetariumb7.db.entity.Mailing;
import com.example.gadgetariumb7.dto.request.MailingRequest;

public interface MailingService {

    void saveMailing(Mailing mailing);

    void sendMailing(MailingRequest mailingRequest);

    void sendEmail(String email, Mailing mailing);

}
