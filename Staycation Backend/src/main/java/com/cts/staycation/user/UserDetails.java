package com.cts.staycation.user;

public interface UserDetails {

	boolean isCredentialsNonExpired();

	boolean isEnabled();

	boolean isAccountNonLocked();

	boolean isAccountNonExpired();

	String getUsername();

	String getPassword();

}
