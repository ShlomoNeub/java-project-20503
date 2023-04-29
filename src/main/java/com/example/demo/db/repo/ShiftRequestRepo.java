package com.example.demo.db.repo;

import com.example.demo.db.entities.ShiftsRequests;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ShiftRequestRepo extends CrudRepository<ShiftsRequests, Integer> {
    Optional<ShiftsRequests> findByUidAndShiftId(Integer uid, Integer shiftId);

    @Query("select s from ShiftsRequests s where s.shiftId = ?1")
    List<ShiftsRequests> getShiftsRequestsByShiftIdFree(Integer shiftId);


}
