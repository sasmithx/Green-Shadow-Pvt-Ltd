package lk.sasax.GreenShadow.service.impl;

import jakarta.transaction.Transactional;
import lk.sasax.GreenShadow.customObj.StaffResponse;
import lk.sasax.GreenShadow.customObj.impl.StaffErrorResponse;
import lk.sasax.GreenShadow.dto.impl.StaffDTO;
import lk.sasax.GreenShadow.entity.impl.Staff;
import lk.sasax.GreenShadow.exception.DataPersistFailedException;
import lk.sasax.GreenShadow.exception.StaffNotFoundException;
import lk.sasax.GreenShadow.repository.StaffRepository;
import lk.sasax.GreenShadow.service.StaffService;
import lk.sasax.GreenShadow.util.AppUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService {
    private final StaffRepository staffRepository;
    private final ModelMapper modelMapper;


    @Override
    public void saveStaff(StaffDTO staffDTO) {
        staffDTO.setId(AppUtil.createStaffId());
        Staff savedStaff =
                staffRepository.save(modelMapper.map(staffDTO, Staff.class));
        if(savedStaff == null) {
            throw new DataPersistFailedException("Staff not saved");
        }
    }

    @Override
    @Transactional
    public void updateStaff(String id, StaffDTO staffDTO) {
        System.out.println("ID is :"+id);
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new StaffNotFoundException("Staff Not found"));
        staff.setName(staffDTO.getName());
        staff.setDob(staffDTO.getDob());
        staff.setGender(staffDTO.getGender());
        staff.setDesignation(staffDTO.getDesignation());
        staff.setRole(staffDTO.getRole());
        staff.setEmail(staffDTO.getEmail());
        staff.setMobile(staffDTO.getMobile());
        staff.setAddress(staffDTO.getAddress());
        staff.setPostalCode(staffDTO.getPostalCode());
        staff.setJoinedDate(staffDTO.getJoinedDate());
        staff.setStatus(staffDTO.getStatus());
        staff.setFields(staffDTO.getFields());
        staff.setVehicles(staffDTO.getVehicles());

       /* if(!staffRepository.existsById(staffDTO.getId())) {
            throw new StaffNotFoundException("Staff not found");
        }else {
            staffRepository.save(modelMapper.map(staffDTO, Staff.class));
        }*/
    }

    @Override
    public void deleteStaff(String id) {
        Optional<Staff> selectedStaff = staffRepository.findById(id);
        if(!selectedStaff.isPresent()) {
            throw new StaffNotFoundException("Staff Not found");
        }else staffRepository.deleteById(id);
    }

    @Override
    public StaffResponse getSelectedStaff(String id) {
        Optional<Staff> byId = staffRepository.findById(id);
        return(byId.isPresent())
            ?modelMapper.map(byId.get(),StaffDTO.class)
                :new StaffErrorResponse(0,"Staff not found");
    }

    @Override
    public List<StaffDTO> getAllStaff() {
        return modelMapper.map(staffRepository.findAll(), List.class);
    }
}
