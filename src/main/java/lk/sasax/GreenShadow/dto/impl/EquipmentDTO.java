package lk.sasax.GreenShadow.dto.impl;

import lk.sasax.GreenShadow.customObj.EquipmentResponse;
import lk.sasax.GreenShadow.dto.SuperDTO;
import lk.sasax.GreenShadow.util.Enum.EquipmentType;
import lk.sasax.GreenShadow.util.Enum.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EquipmentDTO implements SuperDTO, EquipmentResponse {
    private String equipmentId;
    private String name;
    private EquipmentType type;
    private Status status;
    private String staff;
    private String field;
}
