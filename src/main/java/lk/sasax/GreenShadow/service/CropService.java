package lk.sasax.GreenShadow.service;

import lk.sasax.GreenShadow.customObj.CropResponse;
import lk.sasax.GreenShadow.dto.impl.CropDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CropService {
    void saveCrop(CropDTO cropDTO);
    void updateCrop(CropDTO cropDTO);
    void deleteCrop(String id);
    CropResponse getSelectedCrop(String id);
    List<CropDTO> getAllCrops();
}
