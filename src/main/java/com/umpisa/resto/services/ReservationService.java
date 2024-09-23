package com.umpisa.resto.services;

import com.umpisa.resto.error.ReservationError;
import com.umpisa.resto.error.ReservationNotFound;
import com.umpisa.resto.models.Reservation;
import com.umpisa.resto.models.enums.ReservationStatus;
import com.umpisa.resto.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReservationService {

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private SmsSender smsSender;

    @Autowired
    private ReservationRepository reservationRepository;

    /***
     *     Save reservation details and notify guest
     */
    public void create(Reservation reservation) {

        System.out.println("reservation");
        System.out.println(reservation);
        reservationRepository.save(reservation);
        String message = "Hi " + reservation.getName() + ", your seats has been reserved at " + reservation.getReservedDatetime();
        if (reservation.getIsEmailNotify()) {
            emailSender.sendToEmail(reservation.getEmail(), message);
        }
        if (reservation.getIsSmsNotify()) {
            smsSender.sendToSms(reservation.getPhoneNumber(), message);
        }
    }

    /***
     *     Cancel reservation by changing status and notify guest
     */
    public void cancel(String uuid) throws Throwable {
        Reservation reservation = getReservationBy(uuid);
        reservation.setStatus(ReservationStatus.CANCELLED);
        reservationRepository.save(reservation);
        String message = "Hi " + reservation.getName() + ", your reservation has been cancelled.";
        if (reservation.getIsEmailNotify()) {
            emailSender.sendToEmail(reservation.getEmail(), message);
        }
        if (reservation.getIsSmsNotify()) {
            smsSender.sendToSms(reservation.getPhoneNumber(), message);
        }
    }

    /***
     *     Query reservation detail list
     */
    public List<Reservation> view() {
        List<Reservation> reservations = new ArrayList<>();
        reservationRepository.findAll()
                .forEach(reservation -> reservations.add(reservation));
        return reservations;
    }

    /***
     *     Update reservation details and notify guest
     */
    public void update(String uuid, LocalDateTime newReservedDate, Integer newGuestCount) {
        Reservation reservation = getReservationBy(uuid);
        reservation.setReservedDatetime(newReservedDate);
        reservation.setGuestCount(newGuestCount);
        reservation.setUpdatedAt(LocalDateTime.now());
        reservationRepository.save(reservation);

        String message = "Hi " + reservation.getName() + ", your reservation has been updated.";
        if (reservation.getIsEmailNotify()) {
            emailSender.sendToEmail(reservation.getEmail(), message);
        }
        if (reservation.getIsSmsNotify()) {
            smsSender.sendToSms(reservation.getPhoneNumber(), message);
        }
    }

    /***
     *     Process of sending reservation notification prior to 4 hours
     */
    public void sendReminder() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start = now.plusHours(4);
        LocalDateTime end = now.plusHours(5);
        System.out.println("now = " + now);
        System.out.println("start = " + start + ", end =" + end);
        List<Reservation> reservations = reservationRepository.findByReservedDatetimeBetween(start, end);
        reservations.stream()
                .filter(reservation -> reservation.getStatus() == ReservationStatus.CREATED
                        || reservation.getStatus() == ReservationStatus.UPDATED)
                .forEach(reservation -> {
                    sendReservationNotification(reservation);
                    reservation.setStatus(ReservationStatus.NOTIFIED);
                    reservationRepository.save(reservation);
                });
    }

    /***
     *     Get reservation details
     */
    private Reservation getReservationBy(String uuid) {
        Optional<Reservation> result = reservationRepository.findById(UUID.fromString(uuid));
        result.orElseThrow(() -> new ReservationNotFound("Reservation not found"));
        return result.get();
    }

    /***
     *     Send reservation notification via email and sms
     */
    private void sendReservationNotification(Reservation reservation) {
        String message = "Hi " + reservation.getName()
                + ", we would like to remind you that you have a reservation with us at "
                + reservation.getReservedDatetime();
        emailSender.sendToEmail(reservation.getEmail(), message);
        smsSender.sendToSms(reservation.getPhoneNumber(), message);

    }
}
