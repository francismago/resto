package com.umpisa.resto.controllers;

import com.umpisa.resto.models.Reservation;
import com.umpisa.resto.models.ReservationRequest;
import com.umpisa.resto.models.ReservationResponse;
import com.umpisa.resto.models.UpdateReservationRequest;
import com.umpisa.resto.models.enums.ReservationStatus;
import com.umpisa.resto.services.ReservationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


//        Customer Booking System
//        As a Customer, I want to:
//        1. **Create a new reservation**
//        I want to be able to make a reservation by providing my name, phone number, email,
//        reservation date and time, and number of guests. The system should confirm my reservation
//        and notify me through my preferred method of communication (i.e. SMS, Email).
//        2. **Cancel a reservation**
//        If my plans change, I want to cancel my reservation using a reservation ID. I expect to receive
//        a notification confirming the cancellation.
//        3. **View my reservations**
//        I want to be able to view a list of all my upcoming reservations, so I can easily manage my
//        bookings.
//        4. **Update my reservation**
//        I should be able to update the time and number of guests in my reservation
//        Notification System (Stretch Goals)
//        As a Customer, I want to:
//        1. **Receive notifications about my reservation status**
//        I want to be notified about my reservation status through different channels, such as email or
//        SMS. The system should allow me to choose my preferred method of communication and
//        deliver updates accordingly.
//        2. **Receive a reminder**
//        I want to receive a reminder about my reservation 4 hours prior to my reserved timeslot via
//        SMS and email.


@RestController
@RequestMapping("/api/reservations")
public class ReservationsController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping()
    public ReservationResponse createReservation(@RequestBody @Valid ReservationRequest request) {
        reservationService.create(request.toCreateReservation());
        return new ReservationResponse(ReservationStatus.CREATED.name(), null);
    }

    @DeleteMapping("/{id}")
    public ReservationResponse cancelReservation(@PathVariable String id) throws Throwable {
        reservationService.cancel(id);
        return new ReservationResponse(ReservationStatus.CANCELLED.name(), null);
    }

    @GetMapping()
    public ReservationResponse<List<Reservation>> viewReservation() {
        List list = reservationService.view();
        return new ReservationResponse(null, list);
    }

    @PatchMapping("/{id}")
    public ReservationResponse updateReservation(@PathVariable String id, @RequestBody UpdateReservationRequest request) {
        LocalDateTime reservedDate = LocalDateTime.parse(request.reservedDatetime());
        reservationService.update(id, reservedDate, request.guestCount());
        return new ReservationResponse(ReservationStatus.UPDATED.name(), null);
    }

}
