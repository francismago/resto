package com.umpisa.resto.scheduler;

import com.umpisa.resto.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ReservationReminder {

    @Autowired
    private ReservationService reservationService;

    /***
     *     Runs every 30 minutes
     */
    @Scheduled(cron = "0 */30 * * * ?")
    public void scheduleGuestReminder(){
        System.out.println("Run scheduleGuestReminder.....");
        reservationService.sendReminder();
    }
}
