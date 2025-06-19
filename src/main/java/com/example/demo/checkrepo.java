package com.example.demo;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface checkrepo extends CrudRepository<checkin, String> {
    Optional<checkin> findByName(String name);

	checkin findTopByOrderByIdDesc();
}

