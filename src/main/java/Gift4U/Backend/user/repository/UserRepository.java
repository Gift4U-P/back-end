package Gift4U.Backend.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import Gift4U.Backend.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

	Optional<User> findUserByEmail(String email);
}
