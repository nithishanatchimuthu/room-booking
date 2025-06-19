package com.example.demo;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface RoomRepository extends CrudRepository<Room, Long> {
	
    Optional<Room> findById(int id);

	List<Room> findByLocationAndCapacity(String location,int capacity);

	List<Room> findByBooked(boolean booked);
	

}

