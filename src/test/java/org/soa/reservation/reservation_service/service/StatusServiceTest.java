package org.soa.reservation.reservation_service.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.soa.reservation.reservation_service.model.Status;
import org.soa.reservation.reservation_service.repository.StatusRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StatusServiceTest {

    @InjectMocks
    private StatusService statusService;

    @Mock
    private StatusRepository statusRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllStatuses() {
        statusService.getAllStatuses();
        verify(statusRepository, times(1)).findAll();
    }

    @Test
    void testGetStatusById() {
        Long id = 1L;
        Status status = new Status();
        when(statusRepository.findById(id)).thenReturn(Optional.of(status));

        Optional<Status> result = statusService.getStatusById(id);

        assertTrue(result.isPresent());
        assertEquals(status, result.get());
    }

    @Test
    void testCreateStatus() {
        Status status = new Status();
        when(statusRepository.save(status)).thenReturn(status);

        Status result = statusService.createStatus(status);

        assertEquals(status, result);
    }

    @Test
    void testUpdateStatus() {
        Long id = 1L;
        Status status = new Status();
        when(statusRepository.findById(id)).thenReturn(Optional.of(status));
        when(statusRepository.save(any())).thenReturn(status);

        Status updatedStatus = statusService.updateStatus(id, status);

        assertEquals(status, updatedStatus);
    }

    @Test
    void testDeleteStatus() {
        Long id = 1L;
        statusService.deleteStatus(id);
        verify(statusRepository, times(1)).deleteById(id);
    }
}
