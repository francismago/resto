package com.umpisa.resto.services;

public interface SmsSender {
    public void sendToSms(String mobile, String message);
}
