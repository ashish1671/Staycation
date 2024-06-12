package com.cts.staycation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.staycation.model.Role;
import com.cts.staycation.repository.RoleRepository;

import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService {
	@Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }


    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name).get();
    }


}
