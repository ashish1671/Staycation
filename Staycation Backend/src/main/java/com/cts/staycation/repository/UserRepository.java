package com.cts.staycation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.staycation.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	

   
    boolean existsByEmail(String email);

    public User findByEmail(String email);

    void deleteByEmail(String email);


}
