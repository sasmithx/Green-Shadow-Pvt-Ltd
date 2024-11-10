package lk.sasax.GreenShadow.service.impl;

import lk.sasax.GreenShadow.customObj.MonitorLogResponse;
import lk.sasax.GreenShadow.customObj.impl.MonitorLogErrorResponse;
import lk.sasax.GreenShadow.dto.impl.MonitoringLogDTO;
import lk.sasax.GreenShadow.entity.impl.MonitoringLog;
import lk.sasax.GreenShadow.exception.DataPersistFailedException;
import lk.sasax.GreenShadow.exception.LogNotFoundException;
import lk.sasax.GreenShadow.repository.MonitoringLogRepository;
import lk.sasax.GreenShadow.service.MonitoringLogService;
import lk.sasax.GreenShadow.util.AppUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MonitoringLogServiceImpl implements MonitoringLogService {

    private final MonitoringLogRepository monitoringLogRepository;
    private final ModelMapper modelMapper;

    @Override
    public void saveMLog(MonitoringLogDTO monitoringLogDTO) {
        monitoringLogDTO.setLogCode(AppUtil.createMonitoringLogId());
        MonitoringLog savedMonitoringLog =
                monitoringLogRepository.save(modelMapper.map(monitoringLogDTO, MonitoringLog.class));
        if(savedMonitoringLog == null){
            throw new DataPersistFailedException("Monitoring Log Not Saved");
        }
    }

    @Override
    public void updateMLog(MonitoringLogDTO monitoringLogDTO) {
        MonitoringLog monitoringLog = monitoringLogRepository.findById(monitoringLogDTO.getLogCode())
                .orElseThrow( () -> new LogNotFoundException("Log Not Found"));
        monitoringLog.setLogDate(monitoringLogDTO.getLogDate());
        monitoringLog.setObservation(monitoringLogDTO.getObservation());
        monitoringLog.setObservedImage(monitoringLog.getObservedImage());
        monitoringLog.setFields(monitoringLogDTO.getFields());
        monitoringLog.setCrops(monitoringLogDTO.getCrops());
        monitoringLog.setStaffs(monitoringLogDTO.getStaffs());
    }

    @Override
    public void deleteMLog(String id) {
        Optional<MonitoringLog> selectedMonitoringLog = monitoringLogRepository.findById(id);
        if(!selectedMonitoringLog.isPresent()){
            throw new LogNotFoundException("Monitoring Log Not Found");
        }else monitoringLogRepository.deleteById(id);
    }

    @Override
    public MonitorLogResponse getSelectedMLog(String id) {
        Optional<MonitoringLog> byId = monitoringLogRepository.findById(id);
        return (byId.isPresent())
                ? modelMapper.map(byId.get(), MonitoringLogDTO.class)
                : new MonitorLogErrorResponse(0,"Monitoring Log Not Found");
    }

    @Override
    public List<MonitoringLogDTO> getAllMLogs() {
        return modelMapper.map(monitoringLogRepository.findAll(), List.class);
    }
}
