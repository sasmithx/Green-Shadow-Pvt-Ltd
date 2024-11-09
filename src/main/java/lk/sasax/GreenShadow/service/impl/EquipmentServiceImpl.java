package lk.sasax.GreenShadow.service.impl;

import jakarta.transaction.Transactional;
import lk.sasax.GreenShadow.customObj.EquipmentResponse;
import lk.sasax.GreenShadow.customObj.impl.EquipmentErrorResponse;
import lk.sasax.GreenShadow.dto.impl.EquipmentDTO;
import lk.sasax.GreenShadow.entity.impl.Equipment;
import lk.sasax.GreenShadow.entity.impl.Field;
import lk.sasax.GreenShadow.entity.impl.Staff;
import lk.sasax.GreenShadow.exception.DataPersistFailedException;
import lk.sasax.GreenShadow.exception.EquipmentNotFoundException;
import lk.sasax.GreenShadow.repository.EquipmentRepository;
import lk.sasax.GreenShadow.repository.FieldRepository;
import lk.sasax.GreenShadow.repository.StaffRepository;
import lk.sasax.GreenShadow.service.EquipmentService;
import lk.sasax.GreenShadow.util.AppUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EquipmentServiceImpl implements EquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final StaffRepository staffRepository;
    private final FieldRepository fieldRepository;
    private final ModelMapper modelMapper;

    @Override
    public void saveEquipment(EquipmentDTO equipmentDTO) {
        equipmentDTO.setEquipmentId(AppUtil.createEquipmentId());
        Equipment savedEquipment =
                equipmentRepository.save(modelMapper.map(equipmentDTO, Equipment.class));
        if (savedEquipment == null) {
            throw new DataPersistFailedException("Equipment not saved");
        }
    }

    @Override
    @Transactional
    public void updateEquipment(String id, EquipmentDTO equipmentDTO) {
        Equipment equipment = equipmentRepository.findById(id)
                .orElseThrow(() -> new EquipmentNotFoundException("Equipment not found"));
        equipment.setName(equipmentDTO.getName());
        equipment.setType(equipmentDTO.getType());
        equipment.setStatus(equipmentDTO.getStatus());

        Optional<Staff> staffOptional = staffRepository.findById(equipmentDTO.getStaff());
        Staff staff = staffOptional.get();
        equipment.setStaff(staff);

        Optional<Field> fieldOptional = fieldRepository.findById(equipmentDTO.getField());
        Field field = fieldOptional.get();
        equipment.setField(field);
    }

    @Override
    public void deleteEquipment(String id) {
        Optional<Equipment> selectedEquipment = equipmentRepository.findById(id);
      if(!selectedEquipment.isPresent()) {
          throw new EquipmentNotFoundException("Equipment not found");
      }else equipmentRepository.deleteById(id);
    }

    @Override
    public EquipmentResponse getSelectedEquipment(String id) {
        Optional<Equipment> byId = equipmentRepository.findById(id);
        return(byId.isPresent())
                ?modelMapper.map(byId.get(),EquipmentDTO.class)
                :new EquipmentErrorResponse(0,"Equipment not found");
    }

    @Override
    public List<EquipmentDTO> getAllEquipments() {
        return modelMapper.map(equipmentRepository.findAll(), List.class);
    }
}
