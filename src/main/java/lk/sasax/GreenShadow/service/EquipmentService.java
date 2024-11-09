package lk.sasax.GreenShadow.service;

import lk.sasax.GreenShadow.customObj.EquipmentResponse;
import lk.sasax.GreenShadow.dto.impl.EquipmentDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EquipmentService {
    void saveEquipment(EquipmentDTO equipmentDTO);
    void updateEquipment(String id,EquipmentDTO equipmentDTO);
    void deleteEquipment(String id);
    EquipmentResponse getSelectedEquipment(String id);
    List<EquipmentDTO> getAllEquipments();
}
