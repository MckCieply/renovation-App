package com.mckcieply.renovationapp.room;

import com.mckcieply.core.BaseService;
import org.springframework.stereotype.Service;

@Service
public class RoomService extends BaseService<Room, Long> {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        super(roomRepository);
        this.roomRepository = roomRepository;
    }

}
