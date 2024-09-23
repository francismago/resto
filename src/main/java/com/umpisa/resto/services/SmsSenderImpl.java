package com.umpisa.resto.services;

import org.springframework.stereotype.Service;

@Service
public class SmsSenderImpl implements SmsSender {

    @Override
    public void sendToSms(String mobile, String message) {
        System.out.println("Sent SMS");
        System.out.println(String.format("To: %s , message = %s",mobile, message));
    }
}
