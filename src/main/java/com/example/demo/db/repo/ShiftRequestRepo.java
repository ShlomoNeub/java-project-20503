package com.example.demo.db.repo;

import com.example.demo.db.entities.ShiftsRequests;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ShiftRequestRepo extends CrudRepository<ShiftsRequests,Integer> {
    Optional<ShiftsRequests> findByUidAndShiftId(Integer uid, Integer shiftId);

    /**
     * Gets all the Shift Requests by Shift
     * @param shiftId of the source shift
     * @return all shift-requests that have the same shift and equal to shiftId
     */

    List<ShiftsRequests> getShiftsRequestsByShiftId(Integer shiftId);

    /**
     * Gets ShiftsRequests by user id
     * @param uid
     * @return List
     */
    List<ShiftsRequests> getShiftsRequestsByUid(Integer uid);

    /**
     * Gets ShiftsRequests by timestamp
     * @param timestamp
     * @return ShiftsRequests
     */
    List<ShiftsRequests> getShiftsRequestsByTimestamp(java.sql.Timestamp timestamp);

    @Query("select s from ShiftsRequests s where s.shiftId = ?1 and s.schedule is null")
    List<ShiftsRequests> getShiftsRequestsByShiftIdFree(Integer shiftId);


}
