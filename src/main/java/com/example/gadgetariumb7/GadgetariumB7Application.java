package com.example.gadgetariumb7;

import com.stripe.Stripe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.PostConstruct;

@SpringBootApplication
@Controller
public class GadgetariumB7Application {

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @PostConstruct
    public void setup(){
        Stripe.apiKey = stripeApiKey;
    }

    public static void main(String[] args) {
        SpringApplication.run(GadgetariumB7Application.class, args);
        System.out.println("Gadgetarium проектиси");
    }

    @GetMapping("/")
    public String greetings() {
        return "introduction";
    }
}
