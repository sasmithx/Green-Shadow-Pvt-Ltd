package lk.sasax.GreenShadow.service.impl;

import jakarta.transaction.Transactional;
import lk.sasax.GreenShadow.customObj.VehicleResponse;
import lk.sasax.GreenShadow.customObj.impl.VehicleErrorResponse;
import lk.sasax.GreenShadow.dto.impl.VehicleDTO;
import lk.sasax.GreenShadow.entity.impl.Vehicle;
import lk.sasax.GreenShadow.exception.DataPersistFailedException;
import lk.sasax.GreenShadow.exception.VehicleNotFoundException;
import lk.sasax.GreenShadow.repository.VehicleRepository;
import lk.sasax.GreenShadow.service.VehicleService;
import lk.sasax.GreenShadow.util.AppUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleServiceImpl implements VehicleService {
    private final VehicleRepository vehicleRepository;
    private final ModelMapper modelMapper;

    public VehicleServiceImpl(VehicleRepository vehicleRepository, ModelMapper modelMapper) {
        this.vehicleRepository = vehicleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void saveVehicle(VehicleDTO vehicleDTO) {
        vehicleDTO.setVehicleCode(AppUtil.createVehicleId());
        Vehicle savedVehicle =
                vehicleRepository.save(modelMapper.map(vehicleDTO, Vehicle.class));
        if (savedVehicle == null) {
            throw new DataPersistFailedException("Vehicle not saved");
        }
    }

    @Override
    @Transactional
    public void updateVehicle(String id,VehicleDTO vehicleDTO) {
        Vehicle vehicle = vehicleRepository.findById(id)
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
        Optional<Vehicle> selectedVehicle = vehicleRepository.findById(id);
        if (!selectedVehicle.isPresent()) {
            throw new VehicleNotFoundException("Vehicle not found");
        }else vehicleRepository.deleteById(id);
    }

    @Override
    public VehicleResponse getSelectedVehicle(String id) {
        Optional<Vehicle> byId = vehicleRepository.findById(id);
        return(byId.isPresent())
                ?modelMapper.map(byId.get(), VehicleDTO.class)
                :new VehicleErrorResponse(0,"Vehicle not found");
    }

    @Override
    public List<VehicleDTO> getAllVehicles() {
        return modelMapper.map(vehicleRepository.findAll(), List.class);
    }
}
