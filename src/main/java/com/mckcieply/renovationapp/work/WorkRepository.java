package com.mckcieply.renovationapp.work;

import com.mckcieply.core.BaseRepository;
import com.mckcieply.renovationapp.analytics.dto.CostAnalyticsDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing work data.
 * Extends BaseRepository to provide CRUD operations for Work entities.
 */
@Repository
public interface WorkRepository extends BaseRepository<Work, Long> {

    @Query("SELECT new com.mckcieply.renovationapp.analytics.dto.CostAnalyticsDto(" +
            "CAST(SUM(CASE WHEN w.paid = true THEN COALESCE(w.finalLaborCost, 0) ELSE COALESCE(w.estLaborCost, 0) END) AS double), " +
            "CAST(SUM(CASE WHEN w.paid = true THEN COALESCE(w.finalMaterialCost, 0) ELSE COALESCE(w.estMaterialCost, 0) END) AS double)) " +
           "FROM Work w")
    CostAnalyticsDto getCostAnalytics();
}
