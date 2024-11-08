package lk.sasax.GreenShadow.repository;

import lk.sasax.GreenShadow.entity.impl.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends JpaRepository<Staff, String> {}
