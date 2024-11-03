package lk.sasax.GreenShadow.service.impl;

import jakarta.transaction.Transactional;
import lk.sasax.GreenShadow.customObj.VehicleResponse;
import lk.sasax.GreenShadow.customObj.impl.VehicleErrorResponse;
import lk.sasax.GreenShadow.dto.impl.VehicleDTO;
import lk.sasax.GreenShadow.entity.impl.Vehicle;
import lk.sasax.GreenShadow.exception.VehicleNotFoundException;
import lk.sasax.GreenShadow.repo.VehicleRepo;
import lk.sasax.GreenShadow.service.VehicleService;
import lk.sasax.GreenShadow.util.AppUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleServiceImpl implements VehicleService {
    private final VehicleRepo vehicleRepo;
    private ModelMapper modelMapper;

    public VehicleServiceImpl(VehicleRepo vehicleRepo, ModelMapper modelMapper) {
        this.vehicleRepo = vehicleRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public void saveVehicle(VehicleDTO vehicleDTO) {
        vehicleDTO.setVehicleCode(AppUtil.createVehicleId());
        Vehicle savedVehicle =
                vehicleRepo.save(modelMapper.map(vehicleDTO, Vehicle.class));
        if (savedVehicle == null) {
            throw new VehicleNotFoundException("Vehicle not saved");
        }
    }

    @Override
    @Transactional
    public void updateVehicle(String id,VehicleDTO vehicleDTO) {
        Vehicle vehicle = vehicleRepo.findById(id)
                .orElseThrow(() -> new VehicleNotFoundException("Vehicle not found"));
        vehicle.setLicensePlateNumber(vehicleDTO.getLicensePlateNumber());
        vehicle.setVehicleCategory(vehicleDTO.getVehicleCategory());
        vehicle.setFuelType(vehicleDTO.getFuelType());
        vehicle.setStatus(vehicleDTO.getStatus());
        vehicle.setAllocatedStaffMemberDetails(vehicleDTO.getAllocatedStaffMemberDetails());
        vehicle.setRemarks(vehicleDTO.getRemarks());
    }

    @Override
    public void deleteVehicle(String id) {
        Optional<Vehicle> selectedVehicle = vehicleRepo.findById(id);
        if (!selectedVehicle.isPresent()) {
            throw new VehicleNotFoundException("Vehicle not found");
        }else vehicleRepo.deleteById(id);
    }

    @Override
    public VehicleResponse getSelectedVehicle(String id) {
        Optional<Vehicle> byId = vehicleRepo.findById(id);
        return(byId.isPresent())
                ?modelMapper.map(byId.get(), VehicleDTO.class)
                :new VehicleErrorResponse(0,"Vehicle not found");
    }

    @Override
    public List<VehicleDTO> getAllVehicles() {
        return modelMapper.map(vehicleRepo.findAll(), List.class);
    }
}
