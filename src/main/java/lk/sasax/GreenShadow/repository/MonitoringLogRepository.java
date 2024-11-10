package lk.sasax.GreenShadow.repository;

import lk.sasax.GreenShadow.entity.impl.MonitoringLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonitoringLogRepository extends JpaRepository<MonitoringLog,String> {}
