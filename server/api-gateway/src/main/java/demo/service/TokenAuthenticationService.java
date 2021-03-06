package demo.service;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;

import demo.security.jwt.AuthenticatedUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenAuthenticationService {

	private long EXPIRATIONTIME = 1000 * 60 * 60 * 24 * 10; // 10 days
    private String secret = "ThisIsASecret";
    private String tokenPrefix = "Bearer";
    private String headerString = "Authorization";
    
    public void addAuthentication(HttpServletResponse response, String username)
    {
        // Generate token
        String JWT = Jwts.builder()
                    .setSubject(username)
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                    .signWith(SignatureAlgorithm.HS512, secret)
                    .compact();
        System.out.println(JWT);
        response.addHeader(headerString, tokenPrefix + " " + JWT);
    }

	public Authentication getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(headerString);
		String name = request.getHeader("Name");
		if(token != null) { // parse the token.
            String username = Jwts.parser()
                        .setSigningKey(secret)
                        .parseClaimsJws(token)
                        .getBody()
                        .getSubject();
            if(username.equals(name)) {
             	System.out.println(username + " matches " + name);
                return new AuthenticatedUser(username);
            } else {
            	System.out.println(username + " does not match " + name);
            }
        }
		return null;
	}
}
