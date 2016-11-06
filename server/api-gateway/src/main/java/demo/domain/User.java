package demo.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class User {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
 
	@Column(unique=true)
	private String username;
	private String password;
    private String firstName;
    private String lastName;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)  
    @JoinTable(joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
    		   inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))  
    private Set<Role> roles;
    
    public String getFirstName() {
        return firstName;
    }
 
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
 
    public String getLastName() {
        return lastName;
    }
 
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}
	
	public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
