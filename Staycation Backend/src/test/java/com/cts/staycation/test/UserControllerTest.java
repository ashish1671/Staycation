package com.cts.staycation.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cts.staycation.controller.UserController;
import com.cts.staycation.model.User;
import com.cts.staycation.service.IUserService;

public class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    IUserService userService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetUsers() {
        try {
            User user1 = new User(); 
            User user2 = new User(); 
            List<User> userList = Arrays.asList(user1, user2);

            when(userService.getUsers()).thenReturn(userList);

            ResponseEntity<List<User>> response = userController.getUsers();

            assertEquals(HttpStatus.FOUND, response.getStatusCode());
            assertEquals(userList, response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetUserByEmail() {
        try {
            User user = new User(); 
            String email = "test@example.com";

            when(userService.getUser(email)).thenReturn(user);

            ResponseEntity<?> response = userController.getUserByEmail(email);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(user, response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLogin() {
        try {
            User user = new User(); 
            Map<String, Object> loginResponse = new HashMap<>();
            loginResponse.put("status", "success");

            when(userService.login(user)).thenReturn(loginResponse);

            ResponseEntity<Map<String, Object>> response = userController.login(user);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(loginResponse, response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
