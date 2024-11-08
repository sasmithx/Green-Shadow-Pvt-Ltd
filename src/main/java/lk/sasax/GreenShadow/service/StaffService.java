package lk.sasax.GreenShadow.service;

import lk.sasax.GreenShadow.customObj.StaffResponse;
import lk.sasax.GreenShadow.dto.impl.StaffDTO;
import lk.sasax.GreenShadow.entity.impl.Staff;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StaffService {
    void saveStaff(StaffDTO staffDTO);
    void updateStaff(String id,StaffDTO staffDTO);
    void deleteStaff(String id);
    StaffResponse getSelectedStaff(String id);
    List<StaffDTO> getAllStaff();
}
