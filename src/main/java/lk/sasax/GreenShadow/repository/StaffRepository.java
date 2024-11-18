package lk.sasax.GreenShadow.repository;

import lk.sasax.GreenShadow.entity.impl.Staff;
import lk.sasax.GreenShadow.entity.impl.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<Staff, String> {
    Optional<Staff> findByEmail(String email);
}
