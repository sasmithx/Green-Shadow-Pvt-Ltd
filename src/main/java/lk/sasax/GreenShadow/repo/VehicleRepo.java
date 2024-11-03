package lk.sasax.GreenShadow.repo;

import lk.sasax.GreenShadow.entity.impl.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepo extends JpaRepository<Vehicle, String> {}
