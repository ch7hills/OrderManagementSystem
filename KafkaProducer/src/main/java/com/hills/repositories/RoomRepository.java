package com.hills.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hills.model.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room,Long>{
	Room findTopByOrderByIdDesc();
}
