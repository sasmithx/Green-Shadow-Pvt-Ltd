package lk.sasax.GreenShadow.repository;

import lk.sasax.GreenShadow.entity.impl.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment,String> {}
