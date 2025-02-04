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

    // Existing tests remain unchanged

    @Test
    void testFindStatusByName_Success() {
        String statusName = "Preklicano";
        Status status = new Status();
        status.setName(statusName);

        when(statusRepository.findByName(statusName)).thenReturn(Optional.of(status));

        Optional<Status> result = statusService.findStatusByName(statusName);

        assertTrue(result.isPresent());
        assertEquals(statusName, result.get().getName());
    }

    @Test
    void testFindStatusByName_NotFound() {
        String statusName = "NonExistent";
        when(statusRepository.findByName(statusName)).thenReturn(Optional.empty());

        Optional<Status> result = statusService.findStatusByName(statusName);

        assertTrue(result.isEmpty());
    }
}