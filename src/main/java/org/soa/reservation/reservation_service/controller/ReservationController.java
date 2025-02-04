package org.soa.reservation.reservation_service.controller;

import org.soa.reservation.reservation_service.dto.AdminReservationUpdateRequest;
import org.soa.reservation.reservation_service.dto.ReservationCreateRequest;
import org.soa.reservation.reservation_service.dto.ReservationStatusUpdateRequest;
import org.soa.reservation.reservation_service.model.Reservation;
import org.soa.reservation.reservation_service.service.ReservationService;
import org.soa.reservation.reservation_service.service.StatusService;
import org.soa.reservation.reservation_service.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private StatusService statusService;

    @Autowired
    private PaymentService paymentService;

    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        return reservationService.getReservationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationCreateRequest request) {
        Reservation reservation = new Reservation();
        reservation.setDate(request.getDate());
        reservation.setIdCompany(request.getIdCompany());
        reservation.setIdService(request.getIdService());
        reservation.setIdCustomer(request.getIdCustomer());
        reservation.setSendSms(request.getSendSms());
        reservation.setTwoFACode(request.getTwoFACode());
        reservation.setHidden(request.isHidden());
        reservation.setCustomerEmail(request.getCustomerEmail());
        reservation.setCustomerPhoneNumber(request.getCustomerPhoneNumber());
        reservation.setCustomerFullName(request.getCustomerFullName());

        // Set Status if provided
        if (request.getStatusId() != null) {
            reservation.setStatus(statusService.getStatusById(request.getStatusId())
                    .orElseThrow(() -> new RuntimeException("Status not found with id " + request.getStatusId())));
        }

        // Set Payment if provided
        if (request.getPaymentId() != null) {
            reservation.setPayment(paymentService.getPaymentById(request.getPaymentId())
                    .orElseThrow(() -> new RuntimeException("Payment not found with id " + request.getPaymentId())));
        }

        Reservation createdReservation = reservationService.createReservation(reservation);
        return ResponseEntity.ok(createdReservation);
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Reservation> updateReservation(@PathVariable Long id, @RequestBody ReservationUpdateRequest request) {
//        try {
//            Reservation reservation = new Reservation();
//            reservation.setDate(request.getDate());
//            reservation.setIdCompany(request.getIdCompany());
//            reservation.setIdService(request.getIdService());
//            reservation.setIdCustomer(request.getIdCustomer());
//            reservation.setSendSms(request.getSendSms());
//            reservation.setTwoFACode(request.getTwoFACode());
//            reservation.setHidden(request.isHidden());
//            reservation.setCustomerEmail(request.getCustomerEmail());
//            reservation.setCustomerPhoneNumber(request.getCustomerPhoneNumber());
//            reservation.setCustomerFullName(request.getCustomerFullName());
//
//            // Set Status if provided
//            if (request.getStatusId() != null) {
//                reservation.setStatus(statusService.getStatusById(request.getStatusId())
//                        .orElseThrow(() -> new RuntimeException("Status not found with id " + request.getStatusId())));
//            }
//
//            // Set Payment if provided
//            if (request.getPaymentId() != null) {
//                reservation.setPayment(paymentService.getPaymentById(request.getPaymentId())
//                        .orElseThrow(() -> new RuntimeException("Payment not found with id " + request.getPaymentId())));
//            }
//
//            Reservation updatedReservation = reservationService.updateReservation(id, reservation);
//            return ResponseEntity.ok(updatedReservation);
//        } catch (RuntimeException e) {
//            return ResponseEntity.notFound().build();
//        }
//    }

    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservationStatus(@PathVariable Long id, @RequestBody ReservationStatusUpdateRequest request) {
        try {
            Reservation updatedReservation = reservationService.updateReservationStatus(id, request.getStatusId());
            return ResponseEntity.ok(updatedReservation);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/admin/{id}")
    public ResponseEntity<Reservation> adminUpdateReservation(@PathVariable Long id, @RequestBody AdminReservationUpdateRequest request) {
        try {
            Reservation updatedReservation = reservationService.adminUpdateReservation(id, request.getStatusId());
            return ResponseEntity.ok(updatedReservation);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        try {
            reservationService.cancelReservation(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }



}
