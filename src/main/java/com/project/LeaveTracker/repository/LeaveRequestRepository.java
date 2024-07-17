package com.project.LeaveTracker.repository;

import com.project.LeaveTracker.entity.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest,Long> {
    List<LeaveRequest> findByStatus(String status);

    List<LeaveRequest> findByUsername(String username);

    int countByUsernameAndStatus(String username, String status);

    long countByStatus(String status);
}
