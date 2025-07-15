package in.rw.userService.repository;

import in.rw.userService.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    public List<User> findByNameContainingIgnoreCase(String name);
    public List<User> findByRoleName(String role);
}
