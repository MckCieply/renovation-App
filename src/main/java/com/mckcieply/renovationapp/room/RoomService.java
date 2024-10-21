package com.mckcieply.renovationapp.room;

import com.mckcieply.core.BaseService;
import org.springframework.stereotype.Service;

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

}
