package lk.sasax.GreenShadow.dto.impl;

import lk.sasax.GreenShadow.customObj.VehicleResponse;
import lk.sasax.GreenShadow.dto.SuperDTO;
import lk.sasax.GreenShadow.entity.impl.Staff;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VehicleDTO implements SuperDTO, VehicleResponse {
    private String vehicleCode;
    private String licensePlateNo;
    private String category;
    private String fuelType;
    private String status;
    private String staff;
    private String remark;
}
