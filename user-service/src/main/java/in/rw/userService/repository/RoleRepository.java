package in.rw.userService.repository;

import in.rw.userService.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findByNameContainingIgnoreCase(String name);
}
