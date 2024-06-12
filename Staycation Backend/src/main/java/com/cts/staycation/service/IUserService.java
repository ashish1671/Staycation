package com.cts.staycation.service;

import java.util.List;
import java.util.Map;

import com.cts.staycation.model.User;



public interface IUserService {
    User registerUser(User user);
    List<User> getUsers();
    void deleteUser(String email);
    User getUser(String email);
    public Map<String, Object> login(User user);
	public User registerAdmin(User admin);
	User getUserById(Long userId);

}
