package com.mckcieply.renovationapp.room;

import com.mckcieply.core.BaseRepository;
import com.mckcieply.renovationapp.analytics.dto.RoomHealthDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
            "SUM(COALESCE(w.estLaborCost, 0) + COALESCE(w.estMaterialCost, 0)), " +
            "SUM(CASE WHEN w.paid = true THEN COALESCE(w.finalLaborCost, 0) + COALESCE(w.finalMaterialCost, 0) ELSE 0 END)) " +
            "FROM Room r " +
            "LEFT JOIN Work w ON w.room = r " +
            "GROUP BY r.id, r.name, r.budgetPlanned")
    List<RoomHealthDto> getRoomHealthData();

    @Query("SELECT new com.mckcieply.renovationapp.room.RoomBudgetDetailDto(" +
            "r.id, r.name, r.budgetPlanned, " +
            "SUM(COALESCE(w.estLaborCost, 0) + COALESCE(w.estMaterialCost, 0)), " +
            "SUM(CASE WHEN w.paid = true THEN COALESCE(w.finalLaborCost, 0) + COALESCE(w.finalMaterialCost, 0) ELSE 0 END), " +
            "0, " + // unallocated - will be calculated in service
            "CASE WHEN COUNT(w.id) > 0 THEN true ELSE false END) " +
            "FROM Room r " +
            "LEFT JOIN Work w ON w.room = r " +
            "GROUP BY r.id, r.name, r.budgetPlanned")
    List<RoomBudgetDetailDto> getRoomBudgetDetails();

    @Query("SELECT COUNT(w) > 0 FROM Work w WHERE w.room.id = :roomId")
    boolean hasAssociatedWork(@Param("roomId") Long roomId);

    @Query("SELECT SUM(COALESCE(w.estLaborCost, 0) + COALESCE(w.estMaterialCost, 0)) " +
            "FROM Work w")
    Double getTotalEstimatedCosts();

    @Query("SELECT SUM(CASE WHEN w.paid = true THEN COALESCE(w.finalLaborCost, 0) + COALESCE(w.finalMaterialCost, 0) ELSE 0 END) " +
            "FROM Work w")
    Double getTotalPaidCosts();
}
