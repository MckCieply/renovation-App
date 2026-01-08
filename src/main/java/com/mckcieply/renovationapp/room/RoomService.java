package com.mckcieply.renovationapp.room;

import com.mckcieply.core.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing rooms in the renovation application.
 * Provides methods for room operations such as retrieval and processing.
 * Extends BaseService to inherit common CRUD functionalities.
 */
@Service
public class RoomService extends BaseService<Room, Long> {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        super(roomRepository);
        this.roomRepository = roomRepository;
    }

    @Override
    protected Class<Room> getEntityClass() {
        return Room.class;
    }

    /**
     * Validates if room can be deleted (no associated work).
     * @param roomId ID of the room to check
     * @return true if room can be deleted, false if it has associated work
     */
    public boolean canDeleteRoom(Long roomId) {
        return !roomRepository.hasAssociatedWork(roomId);
    }

    /**
     * Gets detailed budget breakdown for all rooms.
     * @return list of room budget details
     */
    public List<RoomBudgetDetailDto> getRoomBudgetDetails() {
        List<RoomBudgetDetailDto> details = roomRepository.getRoomBudgetDetails();

        // Calculate unallocated budget for each room
        details.forEach(detail -> {
            double unallocated = detail.getBudgetPlanned() - detail.getEstimatedCosts() - detail.getPaidCosts();
            detail.setUnallocated(Math.max(0, unallocated));
        });

        return details;
    }

    /**
     * Calculates total budget allocated to all rooms.
     * @return sum of all room budgets
     */
    public double getTotalRoomBudgets() {
        return roomRepository.findAll().stream()
                .mapToDouble(room -> room.getBudgetPlanned() != null ? room.getBudgetPlanned() : 0)
                .sum();
    }

    /**
     * Attempts to delete a room with validation.
     * @param roomId ID of the room to delete
     * @throws IllegalStateException if room has associated work
     */
    @Override
    public void delete(Long roomId) {
        if (!canDeleteRoom(roomId)) {
            throw new IllegalStateException("Cannot delete room - it has associated work items");
        }
        super.delete(roomId);
    }
}
