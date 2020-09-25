package com.hills.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hills.models.Room;
import com.hills.repositories.RoomRepository;

@Service
public class RoomService {
	@Autowired
	RoomRepository roomRepo;
	
	public Room createRoom(Room room) throws RoomNotFoundException {
		if(room.getId()!=0) {
			//throw new RoomNotFoundException("invalid insert operation");
		}
		System.out.println("Before Save:"+room);
		return roomRepo.save(room);
	}
	
	public Room updateRoom(Room room) throws RoomNotFoundException {
		if(room.getId()==0) {
			throw new RoomNotFoundException("invalid update operation");
		}
		return roomRepo.save(room);
	}

}
