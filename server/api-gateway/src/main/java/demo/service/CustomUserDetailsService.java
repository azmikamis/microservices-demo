package demo.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import demo.domain.Role;
import demo.domain.User;
import demo.domain.UserRepository;

@Transactional
public class CustomUserDetailsService implements UserDetailsService {

	private UserRepository userRepository; 
	
	public CustomUserDetailsService(UserRepository userRepository){  
        this.userRepository = userRepository;  
    }
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			User user = userRepository.findByUsername(username);
			if (user == null) {
				System.out.println(username + " not found"); 
                return null;  
            }
			return
				new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthorities(user));
		} catch (Exception e) {
			throw new UsernameNotFoundException("User not found");
		}
	}

	private Set<GrantedAuthority> getAuthorities(User user) {
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();  
        for(Role role : user.getRoles()) {  
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getRole());  
            authorities.add(grantedAuthority);  
        }  
        System.out.println("user authorities are " + authorities.toString());  
        return authorities;
	}
}
