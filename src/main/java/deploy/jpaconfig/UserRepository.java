package deploy.jpaconfig;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import deploy.jsk.entity.Users;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<Users, Long> {
	 Users findByEmail(String email);
}