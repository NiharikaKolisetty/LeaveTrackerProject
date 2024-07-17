package com.project.LeaveTracker.services;

import com.project.LeaveTracker.entity.LeaveRequest;
import com.project.LeaveTracker.repository.LeaveRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LeaveRequestService {

    private LeaveRequestRepository leaveRequestRepository;

    @Autowired
    public LeaveRequestService(LeaveRequestRepository leaveRequestRepository){
        this.leaveRequestRepository = leaveRequestRepository;
    }

    public LeaveRequest saveLeaveRequest(LeaveRequest leaveRequest) {
        return leaveRequestRepository.save(leaveRequest);
    }

    public Optional<LeaveRequest> findLeaveRequestById(Long id) {
        return leaveRequestRepository.findById(id);
    }

    public void updateLeaveStatus(Long id, String status) {
        Optional<LeaveRequest> leaveRequestOpt = leaveRequestRepository.findById(id);
        if (leaveRequestOpt.isPresent()) {
            LeaveRequest leaveRequest = leaveRequestOpt.get();
            leaveRequest.setStatus(status);
            leaveRequestRepository.save(leaveRequest);
        }
    }

    public List<LeaveRequest> findAll() {
        return leaveRequestRepository.findAll();
    }

    public List<LeaveRequest> findByStatus(String status) {
        return leaveRequestRepository.findByStatus(status);
    }

    public List<LeaveRequest> findByUsername(String username) {
        return leaveRequestRepository.findByUsername(username);
    }

    public int getPendingLeavesCount(String username) {
        return leaveRequestRepository.countByUsernameAndStatus(username, "PENDING");
    }

    public int getApprovedLeavesCount(String username) {
        return leaveRequestRepository.countByUsernameAndStatus(username, "APPROVED");
    }

    public int getRejectedLeavesCount(String username) {
        return leaveRequestRepository.countByUsernameAndStatus(username, "REJECTED");
    }

    public long getTotalPendingLeavesCount() {
        return leaveRequestRepository.countByStatus("PENDING");
    }

    public long getTotalApprovedLeavesCount() {
        return leaveRequestRepository.countByStatus("APPROVED");
    }

    public long getTotalDeclinedLeavesCount() {
        return leaveRequestRepository.countByStatus("DECLINED");
    }
}
