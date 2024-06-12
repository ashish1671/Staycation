package com.cts.staycation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.staycation.exception.UserAlreadyExistsException;
import com.cts.staycation.model.User;
import com.cts.staycation.service.IUserService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
    private IUserService userService;
	
	@CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/register-admin")
    public ResponseEntity<?> registerAdmin(@RequestBody User admin){
        try{
            userService.registerAdmin(admin);
            return ResponseEntity.ok("Admin registration successful!");

        }catch (UserAlreadyExistsException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
    
	@CrossOrigin(origins = "http://localhost:5173")
    @DeleteMapping("/delete-admin/{email}")
    public ResponseEntity<String> deleteAdmin(@PathVariable("email") String email){
        try{
            userService.deleteUser(email); 
            return ResponseEntity.ok("Admin deleted successfully");
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting admin: " + e.getMessage());
        }
    }
    
	@CrossOrigin(origins = "http://localhost:5173")
    @DeleteMapping("/delete-user/{email}")
    public ResponseEntity<String> deleteUser(@PathVariable("email") String email){
        try{
            userService.deleteUser(email);
            return ResponseEntity.ok("User deleted successfully");
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting user: " + e.getMessage());
        }
    }


    
}



