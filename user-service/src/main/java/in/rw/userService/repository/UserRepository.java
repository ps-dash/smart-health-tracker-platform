package in.rw.userService.repository;

import in.rw.userService.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public List<User> findByNameContainingIgnoreCase(String name);
    public List<User> findByRoleName(String role);

    @Query("""
            Select u from User u
            where (:mobile is not null and u.mobile = :mobile)
               or (:email is not null and u.email = :email)
           """)
    public Optional<User> findUserByMobileOrEmail(
            @Param("mobile") String mobile, @Param("email") String email);
}
