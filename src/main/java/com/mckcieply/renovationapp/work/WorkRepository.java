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
            "SUM(w.finalLaborCost), " +
            "SUM(w.finalMaterialCost)) " +
           "FROM Work w")
    CostAnalyticsDto getCostAnalytics();
}
