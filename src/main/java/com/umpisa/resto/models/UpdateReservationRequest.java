package com.umpisa.resto.models;

import jakarta.validation.constraints.NotNull;

public record UpdateReservationRequest(@NotNull String reservedDatetime,
                                       @NotNull Integer guestCount) {
}
