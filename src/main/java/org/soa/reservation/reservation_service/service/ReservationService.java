package org.soa.reservation.reservation_service.service;

import org.soa.reservation.reservation_service.model.Reservation;
import org.soa.reservation.reservation_service.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    // Get all reservations
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    // Get a reservation by ID
    public Optional<Reservation> getReservationById(Long id) {
        return reservationRepository.findById(id);
    }

    // Create a new reservation
    public Reservation createReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    // Update an existing reservation
    public Reservation updateReservation(Long id, Reservation updatedReservation) {
        return reservationRepository.findById(id)
                .map(existingReservation -> {
                    existingReservation.setDate(updatedReservation.getDate());
                    existingReservation.setIdCompany(updatedReservation.getIdCompany());
                    existingReservation.setIdService(updatedReservation.getIdService());
                    existingReservation.setIdCustomer(updatedReservation.getIdCustomer());
                    existingReservation.setSendSms(updatedReservation.getSendSms());
                    existingReservation.setTwoFACode(updatedReservation.getTwoFACode());
                    existingReservation.setHidden(updatedReservation.isHidden());
                    existingReservation.setCustomerEmail(updatedReservation.getCustomerEmail());
                    existingReservation.setCustomerPhoneNumber(updatedReservation.getCustomerPhoneNumber());
                    existingReservation.setCustomerFullName(updatedReservation.getCustomerFullName());
                    existingReservation.setStatus(updatedReservation.getStatus());
                    existingReservation.setPayment(updatedReservation.getPayment());
                    return reservationRepository.save(existingReservation);
                })
                .orElseThrow(() -> new RuntimeException("Reservation not found with id " + id));
    }

    // Delete a reservation by ID
    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}
