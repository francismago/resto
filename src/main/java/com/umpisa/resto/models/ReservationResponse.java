package com.umpisa.resto.models;

public record ReservationResponse<T>(String status, T data) {}