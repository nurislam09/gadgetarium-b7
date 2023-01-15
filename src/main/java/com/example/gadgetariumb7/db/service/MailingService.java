package com.example.gadgetariumb7.db.service;

import com.example.gadgetariumb7.db.entity.Mailing;
import com.example.gadgetariumb7.db.entity.Subscription;
import com.example.gadgetariumb7.db.repository.MailingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MailingService {
    private final SubscriptionService subscriptionService;
    private final JavaMailSender emailSender;
    private final MailingRepository mailingRepository;

    public void saveMailing(Mailing mailing) {
        mailingRepository.save(mailing);
    }

    public void sendMailing(Mailing mailing) {
        List<Subscription> subscribers = subscriptionService.findAll();
        for (Subscription subscriber : subscribers) {
            sendEmail(subscriber.getEmail(), mailing);
        }
        saveMailing(mailing);
    }

    public void sendEmail(String email, Mailing mailing) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("zhumataev.03@gmail.com");
        message.setTo(email);
        message.setSubject(mailing.getMailingName());
        message.setText(mailing.getDescription() + "\n" + mailing.getImage()
                + "\n" + mailing.getMailingDateOfStart() + mailing.getMailingDateOfEnd());
        message.setBcc();
        this.emailSender.send(message);
    }
}
