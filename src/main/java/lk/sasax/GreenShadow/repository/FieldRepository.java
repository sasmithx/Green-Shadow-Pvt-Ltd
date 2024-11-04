package lk.sasax.GreenShadow.repository;

import lk.sasax.GreenShadow.entity.impl.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldRepository extends JpaRepository<Field, String> {
}
