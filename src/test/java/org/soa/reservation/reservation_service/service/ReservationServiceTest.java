package org.soa.reservation.reservation_service.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.soa.reservation.reservation_service.model.Reservation;
import org.soa.reservation.reservation_service.repository.ReservationRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservationServiceTest {

    @InjectMocks
    private ReservationService reservationService;

    @Mock
    private ReservationRepository reservationRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllReservations() {
        reservationService.getAllReservations();
        verify(reservationRepository, times(1)).findAll();
    }

    @Test
    void testGetReservationById() {
        Long id = 1L;
        Reservation reservation = new Reservation();
        when(reservationRepository.findById(id)).thenReturn(Optional.of(reservation));

        Optional<Reservation> result = reservationService.getReservationById(id);

        assertTrue(result.isPresent());
        assertEquals(reservation, result.get());
    }

    @Test
    void testCreateReservation() {
        Reservation reservation = new Reservation();
        when(reservationRepository.save(reservation)).thenReturn(reservation);

        Reservation result = reservationService.createReservation(reservation);

        assertEquals(reservation, result);
    }

    @Test
    void testUpdateReservation() {
        Long id = 1L;
        Reservation reservation = new Reservation();
        when(reservationRepository.findById(id)).thenReturn(Optional.of(reservation));
        when(reservationRepository.save(any())).thenReturn(reservation);

        Reservation updatedReservation = reservationService.updateReservation(id, reservation);

        assertEquals(reservation, updatedReservation);
    }

    @Test
    void testDeleteReservation() {
        Long id = 1L;
        reservationService.deleteReservation(id);
        verify(reservationRepository, times(1)).deleteById(id);
    }
}
