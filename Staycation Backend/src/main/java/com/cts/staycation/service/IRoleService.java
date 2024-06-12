package com.cts.staycation.service;

import java.util.List;

import com.cts.staycation.model.Role;



public interface IRoleService {
    List<Role> getRoles();

    Role findByName(String name);
}
