package com.example.demo;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private manage userRepository; // 'manage' should be your repository interface

    // Signup logic
    public boolean signup(String username, String password) {
        Optional<userdata> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isPresent()) {
            // User already exists
            return false;
        } else {
            // Create new user
        	userdata newUser = new userdata();
            newUser.setUsername(username);
            newUser.setPassword(password); // In production, always hash the password!
            userRepository.save(newUser);
            return true;
        }
    }

    // Login logic
    public boolean login(String username, String password) {
        Optional<userdata> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isPresent()) {
        	userdata existingUser = optionalUser.get();
            return existingUser.getPassword().equals(password); // Compare passwords (plaintext here, use encryption in real apps)
        }
        return false;
    }
}





//package com.example.demo;
//
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//	public class UserService {
//
//	    @Autowired
//	    private  manage ab;
//
//	    public  boolean login(String username, String password) {
//	        Optional<users> optionalUser = ab.findByUsername(username);
//
//	        if (optionalUser.isPresent()) {
//	            users user = optionalUser.get();
//	            return user.getPassword().equals(password); // For production, use encrypted passwords!
//	        }
//
//	        return false;
//	    }
//	}



