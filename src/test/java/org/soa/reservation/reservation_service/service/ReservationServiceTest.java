package org.soa.reservation.reservation_service.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.soa.reservation.reservation_service.model.Reservation;
import org.soa.reservation.reservation_service.model.Status;
import org.soa.reservation.reservation_service.repository.ReservationRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservationServiceTest {

    @InjectMocks
    private ReservationService reservationService;

    @Mock
    private ReservationRepository reservationRepository;


    @Mock
    private StatusService statusService;


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

//    @Test
//    void testUpdateReservation() {
//        Long id = 1L;
//        Reservation reservation = new Reservation();
//        when(reservationRepository.findById(id)).thenReturn(Optional.of(reservation));
//        when(reservationRepository.save(any())).thenReturn(reservation);
//
//        Reservation updatedReservation = reservationService.updateReservation(id, reservation);
//
//        assertEquals(reservation, updatedReservation);
//    }

    @Test
    void testDeleteReservation() {
        Long id = 1L;
        reservationService.deleteReservation(id);
        verify(reservationRepository, times(1)).deleteById(id);
    }


    @Test
    void testUpdateReservationStatus_Success() {
        Long reservationId = 1L;
        Long statusId = 2L;
        Reservation reservation = new Reservation();
        Status status = new Status();

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));
        when(statusService.getStatusById(statusId)).thenReturn(Optional.of(status));
        when(reservationRepository.save(reservation)).thenReturn(reservation);

        Reservation result = reservationService.updateReservationStatus(reservationId, statusId);

        assertEquals(status, result.getStatus());
        verify(reservationRepository).save(reservation);
    }

    @Test
    void testUpdateReservationStatus_ReservationNotFound() {
        Long reservationId = 1L;
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                reservationService.updateReservationStatus(reservationId, 2L)
        );
    }

    @Test
    void testUpdateReservationStatus_StatusNotFound() {
        Long reservationId = 1L;
        Reservation reservation = new Reservation();
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));
        when(statusService.getStatusById(any())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                reservationService.updateReservationStatus(reservationId, 2L)
        );
    }

    @Test
    void testCancelReservation_Success() {
        Long reservationId = 1L;
        Reservation reservation = new Reservation();
        Status canceledStatus = new Status();
        canceledStatus.setName("Preklicano");

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));
        when(statusService.findStatusByName("Preklicano")).thenReturn(Optional.of(canceledStatus));
        when(reservationRepository.save(reservation)).thenReturn(reservation);

        reservationService.cancelReservation(reservationId);

        assertEquals(canceledStatus, reservation.getStatus());
        verify(reservationRepository).save(reservation);
    }

    @Test
    void testCancelReservation_ReservationNotFound() {
        Long reservationId = 1L;
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                reservationService.cancelReservation(reservationId)
        );
    }

    @Test
    void testCancelReservation_StatusPreklicanoNotFound() {
        Long reservationId = 1L;
        Reservation reservation = new Reservation();
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));
        when(statusService.findStatusByName("Preklicano")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                reservationService.cancelReservation(reservationId)
        );
    }

    @Test
    void testAdminUpdateReservation_Success() {
        Long reservationId = 1L;
        Long statusId = 2L;
        Reservation reservation = new Reservation();
        Status status = new Status();

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));
        when(statusService.getStatusById(statusId)).thenReturn(Optional.of(status));
        when(reservationRepository.save(reservation)).thenReturn(reservation);

        Reservation result = reservationService.adminUpdateReservation(reservationId, statusId);

        assertEquals(status, result.getStatus());
        assertTrue(result.isHidden());
        verify(reservationRepository).save(reservation);
    }

    @Test
    void testAdminUpdateReservation_ReservationNotFound() {
        Long reservationId = 1L;
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                reservationService.adminUpdateReservation(reservationId, 2L)
        );
    }

    @Test
    void testAdminUpdateReservation_StatusNotFound() {
        Long reservationId = 1L;
        Reservation reservation = new Reservation();
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));
        when(statusService.getStatusById(any())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                reservationService.adminUpdateReservation(reservationId, 2L)
        );
    }

}
