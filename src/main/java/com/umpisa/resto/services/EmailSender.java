package com.umpisa.resto.services;

public interface EmailSender {
    public void sendToEmail(String recipient, String message);
}
