package lk.sasax.GreenShadow.dto.impl;

import lk.sasax.GreenShadow.customObj.MonitorLogResponse;
import lk.sasax.GreenShadow.dto.SuperDTO;
import lk.sasax.GreenShadow.entity.impl.Crop;
import lk.sasax.GreenShadow.entity.impl.Field;
import lk.sasax.GreenShadow.entity.impl.Staff;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MonitoringLogDTO implements SuperDTO, MonitorLogResponse {
    private String logCode;
    private Date logDate;
    private String observation;
    private String observedImage;
    private List<Field> fields;
    private List<Crop> crops;
    private List<Staff> staffs;
}
