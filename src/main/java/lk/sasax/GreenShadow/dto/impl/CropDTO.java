package lk.sasax.GreenShadow.dto.impl;

import lk.sasax.GreenShadow.customObj.CropResponse;
import lk.sasax.GreenShadow.dto.SuperDTO;
import lk.sasax.GreenShadow.entity.impl.Field;
import lk.sasax.GreenShadow.entity.impl.MonitoringLog;
import lk.sasax.GreenShadow.util.Enum.AvailabilityStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CropDTO implements SuperDTO, CropResponse {
    private String cropCode;
    private String commonName;
    private String scientificName;
    private String category;
    private String season;
    private AvailabilityStatus status;
    private String cropImage;
    private Field field;
    private List<MonitoringLog> monitoringLogs;
}
