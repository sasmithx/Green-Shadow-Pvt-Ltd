package lk.sasax.GreenShadow.service;

import lk.sasax.GreenShadow.customObj.MonitorLogResponse;
import lk.sasax.GreenShadow.dto.impl.MonitoringLogDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MonitoringLogService {
    void saveMLog(MonitoringLogDTO monitoringLogDTO);
    void updateMLog(MonitoringLogDTO monitoringLogDTO);
    void deleteMLog(String id);
    MonitorLogResponse getSelectedMLog(String id);
    List<MonitoringLogDTO> getAllMLogs();
}
