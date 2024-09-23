package com.umpisa.resto.repositories;

import com.umpisa.resto.models.Reservation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, UUID> {

    List<Reservation> findByReservedDatetimeBetween(LocalDateTime date1, LocalDateTime date2);

}
