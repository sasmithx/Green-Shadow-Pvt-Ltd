package lk.sasax.GreenShadow.repository;

import lk.sasax.GreenShadow.entity.impl.User;
import lk.sasax.GreenShadow.util.Enum.AccessRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
   /* Boolean existsByEmail(String email);
    User findByEmailAndRole(String email, AccessRole role);
    void deleteByEmail(String email);*/
    Optional<User> findByEmail(String email);
}
