package com.umpisa.resto.services;

import org.springframework.stereotype.Service;

@Service
public class EmailSenderImpl implements EmailSender{

    @Override
    public void sendToEmail(String recipient, String message) {
        System.out.println("Sent Email");
        System.out.println(String.format("To: %s , message = %s",recipient, message));
    }
}
