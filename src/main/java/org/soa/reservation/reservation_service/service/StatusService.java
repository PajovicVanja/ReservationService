package org.soa.reservation.reservation_service.service;

import org.soa.reservation.reservation_service.model.Status;
import org.soa.reservation.reservation_service.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatusService {

    @Autowired
    private StatusRepository statusRepository;

    // Get all statuses
    public List<Status> getAllStatuses() {
        return statusRepository.findAll();
    }

    // Get a status by ID
    public Optional<Status> getStatusById(Long id) {
        return statusRepository.findById(id);
    }

    // Create a new status
    public Status createStatus(Status status) {
        return statusRepository.save(status);
    }

    // Update an existing status
    public Status updateStatus(Long id, Status updatedStatus) {
        return statusRepository.findById(id)
                .map(existingStatus -> {
                    existingStatus.setName(updatedStatus.getName());
                    existingStatus.setAddedBy(updatedStatus.getAddedBy());
                    return statusRepository.save(existingStatus);
                })
                .orElseThrow(() -> new RuntimeException("Status not found with id " + id));
    }

    // Delete a status by ID
    public void deleteStatus(Long id) {
        statusRepository.deleteById(id);
    }
}
