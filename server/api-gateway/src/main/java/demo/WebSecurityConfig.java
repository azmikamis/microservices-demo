package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import demo.domain.UserRepository;
import demo.security.jwt.JWTAuthenticationFilter;
import demo.security.jwt.JWTLoginFilter;
import demo.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return new CustomUserDetailsService(userRepository);
    }
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        // disable caching
        http.headers().cacheControl();

        http
        	.csrf().disable() // disable csrf for our requests.
            .authorizeRequests()
            	.antMatchers("/").permitAll()
            	//.antMatchers(HttpMethod.POST, "/users").permitAll()
            	.antMatchers(HttpMethod.POST, "/register").permitAll()
            	.antMatchers(HttpMethod.POST, "/users").permitAll()
            	.anyRequest().authenticated()
            	.and()
            // filter the api/login requests
            .addFilterBefore(new JWTLoginFilter("/login", authenticationManager()), UsernamePasswordAuthenticationFilter.class)
            // filter other requests to check the presence of JWT in header
            .addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	    // Create a default account
//		auth
//			.inMemoryAuthentication()
//				.withUser("user").password("password").roles("USER")
//			.and()
//				.withUser("admin").password("admin").roles("USER", "ADMIN", "READER", "WRITER")
//			.and()
//				.withUser("audit").password("audit").roles("USER", "ADMIN", "READER");
		auth.userDetailsService(userDetailsServiceBean());
	 }
}
