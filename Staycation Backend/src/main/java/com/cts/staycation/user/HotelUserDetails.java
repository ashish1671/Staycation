package com.cts.staycation.user;

import com.cts.staycation.model.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HotelUserDetails implements UserDetails {
	
	
    private Long id;
	
    private String email;
   



	public HotelUserDetails(Long id, String email, String password) {
		
		this.id = id;
		this.email = email;
		this.password = password;
		
	}



	public Long getId() {
		return id;
	}



	public String getEmail() {
		return email;
	}

	private String password;
  

    public static HotelUserDetails buildUserDetails(User user){
        
             
        return new HotelUserDetails(
                user.getId(),
                user.getEmail(),
                user.getPassword());

    }


    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
