package com.umpisa.resto.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.umpisa.resto.models.Reservation;
import com.umpisa.resto.models.ReservationResponse;
import com.umpisa.resto.models.enums.ReservationStatus;
import com.umpisa.resto.services.ReservationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@WebMvcTest(controllers = {ReservationsController.class})
public class ReservationsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    Reservation reserve1 = Reservation.builder()
            .id(UUID.randomUUID())
            .name("Francis M.")
            .email("francis@gmail.com")
            .phoneNumber("09209725507")
            .guestCount(4)
            .reservedDatetime(LocalDateTime.parse("2024-09-22T14:00:00"))
            .isEmailNotify(true)
            .isSmsNotify(true)
            .status(ReservationStatus.CREATED)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
    Reservation reserve2 = Reservation.builder()
            .id(UUID.randomUUID())
            .name("Jerald T.")
            .email("jerald@gmail.com")
            .phoneNumber("09219725507")
            .guestCount(5)
            .reservedDatetime(LocalDateTime.parse("2024-09-22T20:00:00"))
            .isEmailNotify(false)
            .isSmsNotify(true)
            .status(ReservationStatus.CREATED)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
    private final List<Reservation> baseReservations  = Arrays.asList(reserve1, reserve2);

    @Test
    public void whenViewReservations_thenReturnList() throws Exception {
        Mockito.when(reservationService.view()).thenReturn(baseReservations);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        ReservationResponse<Reservation> response = new ReservationResponse(null, baseReservations);

        ResultActions resultActions =  mockMvc.perform(MockMvcRequestBuilders.get("/api/reservations"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect( MockMvcResultMatchers.content().json(mapper.writeValueAsString(response)));

        Mockito.verify(reservationService, Mockito.times(1)).view();
    }
}
