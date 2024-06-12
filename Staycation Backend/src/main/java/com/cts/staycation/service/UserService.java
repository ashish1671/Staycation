package com.cts.staycation.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.cts.staycation.exception.UserAlreadyExistsException;
import com.cts.staycation.model.Role;
import com.cts.staycation.model.User;
import com.cts.staycation.repository.RoleRepository;
import com.cts.staycation.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
    private RoleRepository roleRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
    public User registerUser(User user) {
        return register(user, "ROLE_USER");
    }

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }
    
 
    
    public Map<String, Object> login(User user) {
        User storedUser = userRepository.findByEmail(user.getEmail());
        if (storedUser != null && passwordEncoder.matches(user.getPassword(), storedUser.getPassword())) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "Logged in!");
            response.put("roles", storedUser.getRoles());
            response.put("id", storedUser.getId());
            return response;
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials!");
        }
    }


    private User register(User user, String role) {
        if (userRepository.existsByEmail(user.getEmail())){
            throw new UserAlreadyExistsException(user.getEmail() + " already exists");
        }
        Role userRole = roleRepository.findByName(role).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        user.setRoles(Collections.singleton(userRole));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }
    
    @Override
    public User getUser(String email) {
        User theUser = userRepository.findByEmail(email);
        if (theUser == null){
            throw new EntityNotFoundException("User not found with email: " + email);
        }
        return theUser;
    }
    @Override
    public User registerAdmin(User admin) {
        return register(admin, "ROLE_ADMIN");
    }

    


    @Transactional
    @Override
    public void deleteUser(String email) {
        User theUser = getUser(email); 
        if (theUser != null) {
            userRepository.deleteByEmail(email);
        }
    }
}

