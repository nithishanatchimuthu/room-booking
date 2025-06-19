package com.example.demo;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface manage extends CrudRepository<userdata, String> {
    Optional<userdata> findByUsername(String username);
}

