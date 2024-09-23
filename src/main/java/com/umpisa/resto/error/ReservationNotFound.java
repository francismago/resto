package com.umpisa.resto.error;

public class ReservationNotFound extends RuntimeException{

    public ReservationNotFound(String message){
        super(message);
    }
}
