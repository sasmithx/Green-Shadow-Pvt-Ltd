package lk.sasax.GreenShadow.repository;

import lk.sasax.GreenShadow.entity.impl.Crop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CropRepository extends JpaRepository<Crop, String> {}
