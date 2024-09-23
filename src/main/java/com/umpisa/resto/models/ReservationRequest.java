package com.umpisa.resto.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.umpisa.resto.models.enums.ReservationStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record ReservationRequest(@NotNull String name,
                                 @NotNull @Email(message = "Must be valid email") String email,
                                 @NotNull @Size(min = 11, max = 13, message = "Must be valid phone number") String phoneNumber,
                                 @NotNull @JsonFormat(pattern="yyyy-MM-ddTHH:mm:ss") String reservedDatetime,
                                 @NotNull Integer guestCount,
                                 @NotNull Boolean isSmsNotify,
                                 @NotNull Boolean isEmailNotify) {

    public Reservation toCreateReservation() {
        ;
        return Reservation.builder()
                .name(name)
                .email(email)
                .phoneNumber(phoneNumber)
                .guestCount(guestCount)
                .reservedDatetime(LocalDateTime.parse(reservedDatetime))
                .isEmailNotify(isEmailNotify)
                .isSmsNotify(isSmsNotify)
                .status(ReservationStatus.CREATED)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

    }
}


