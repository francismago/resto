package com.umpisa.resto.models;

import com.umpisa.resto.models.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;




@Getter
@Setter
@Builder()
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@ToString
@Entity
@Table(name = "Reservation")
public class Reservation {
    @Id
    @UuidGenerator
    private UUID id;
    private String name;
    private String phoneNumber;
    private String email;
    private LocalDateTime reservedDatetime;
    private Integer guestCount;
    private Boolean isSmsNotify;
    private Boolean isEmailNotify;
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
