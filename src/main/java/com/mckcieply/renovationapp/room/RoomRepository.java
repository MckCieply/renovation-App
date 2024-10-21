package com.mckcieply.renovationapp.room;

import com.mckcieply.core.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing room data.
 * Extends BaseRepository to provide CRUD operations for Room entities.
 */
@Repository
public interface RoomRepository extends BaseRepository<Room, Long> {
}
