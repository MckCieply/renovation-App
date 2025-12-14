package com.mckcieply.renovationapp.room;

import com.mckcieply.core.BaseRepository;
import com.mckcieply.renovationapp.analytics.dto.RoomHealthDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for accessing room data.
 * Extends BaseRepository to provide CRUD operations for Room entities.
 */
@Repository
public interface RoomRepository extends BaseRepository<Room, Long> {

    @Query("SELECT new com.mckcieply.renovationapp.analytics.dto.RoomHealthDto(" +
            "r.name, " +
            "r.budgetPlanned, " +
            "SUM(COALESCE(w.finalLaborCost, 0) + COALESCE(w.finalMaterialCost, 0))) " +
            "FROM Room r " +
            "LEFT JOIN Work w ON w.room = r " +
            "GROUP BY r.id, r.name, r.budgetPlanned")
    List<RoomHealthDto> getRoomHealthData();
}
