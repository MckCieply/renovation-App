package com.mckcieply.renovationapp.analytics;

import com.mckcieply.renovationapp.analytics.dto.CostAnalyticsDto;
import com.mckcieply.renovationapp.analytics.dto.RoomHealthDto;
import com.mckcieply.renovationapp.room.RoomRepository;
import com.mckcieply.renovationapp.work.WorkRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnalyticsService {
    private final WorkRepository workRepository;
    private final RoomRepository roomRepository;


    public AnalyticsService(WorkRepository workRepository, RoomRepository roomRepository) {
        this.workRepository = workRepository;
        this.roomRepository = roomRepository;
    }

    public CostAnalyticsDto getCostData() {
        return workRepository.getCostAnalytics();
    }


    public List<RoomHealthDto> getRoomHealth() {
        return roomRepository.getRoomHealthData();
    }
}
