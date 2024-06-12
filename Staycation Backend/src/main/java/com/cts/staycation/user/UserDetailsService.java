package com.cts.staycation.user;

public interface UserDetailsService {

	UserDetails loadUserByUsername(String email);

}
