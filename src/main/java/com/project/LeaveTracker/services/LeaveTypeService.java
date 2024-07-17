package com.project.LeaveTracker.services;

import com.project.LeaveTracker.entity.LeaveType;
import com.project.LeaveTracker.repository.LeaveTypeRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class LeaveTypeService {
    private final LeaveTypeRepository leaveTypeRepository;

    @Autowired
    public LeaveTypeService(LeaveTypeRepository leaveTypeRepository) {
        this.leaveTypeRepository = leaveTypeRepository;
    }

    public void save(@Valid LeaveType leaveType) {
        leaveTypeRepository.save(leaveType);
    }

    public List<LeaveType> getAllLeaveTypes(){
        return leaveTypeRepository.findAll();
    }

    public void deleteLeaveTypeById(Long id){
        leaveTypeRepository.deleteById(id);
    }

    public LeaveType findById(Long id) {
        Optional<LeaveType> leaveType = leaveTypeRepository.findById(id);
        return leaveType.orElse(null);
    }

    public long getTotalCount(){
        return leaveTypeRepository.count();
    }
}
