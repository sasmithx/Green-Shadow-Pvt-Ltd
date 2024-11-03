package lk.sasax.GreenShadow.service;


import lk.sasax.GreenShadow.customObj.VehicleResponse;
import lk.sasax.GreenShadow.dto.impl.VehicleDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VehicleService {
    void saveVehicle(VehicleDTO vehicleDTO);
    void updateVehicle(String id,VehicleDTO vehicleDTO);
    void deleteVehicle(String id);
    VehicleResponse getSelectedVehicle(String id);
    List<VehicleDTO> getAllVehicles();
}
