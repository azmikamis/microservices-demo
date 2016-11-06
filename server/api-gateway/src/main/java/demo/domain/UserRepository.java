package demo.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface UserRepository extends CrudRepository<User, Long> {

	List<User> findByUsernameAndPassword(String username, String password);

	User findByUsername(String username);
}