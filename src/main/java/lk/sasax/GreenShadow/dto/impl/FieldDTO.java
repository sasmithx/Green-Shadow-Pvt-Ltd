package lk.sasax.GreenShadow.dto.impl;

import lk.sasax.GreenShadow.customObj.FieldResponse;
import lk.sasax.GreenShadow.dto.SuperDTO;
import lk.sasax.GreenShadow.entity.impl.Crop;
import lk.sasax.GreenShadow.entity.impl.Equipment;
import lk.sasax.GreenShadow.entity.impl.MonitoringLog;
import lk.sasax.GreenShadow.entity.impl.Staff;
import lk.sasax.GreenShadow.util.Enum.AvailabilityStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FieldDTO implements SuperDTO, FieldResponse {
    private String fCode;
    private String fieldName;
    private Double fieldSize;
    private Point fieldLocation;
    private String fieldImage1;
    private String fieldImage2;
    private AvailabilityStatus status;
    private List<Crop> crops;
    private List<Staff> staffs;
    private List<Equipment> equipments;
    private List<MonitoringLog> monitoringLogs;
}
