package lk.sasax.GreenShadow.service;

import lk.sasax.GreenShadow.customObj.FieldResponse;
import lk.sasax.GreenShadow.dto.impl.CropDTO;
import lk.sasax.GreenShadow.dto.impl.FieldDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FieldService {
    void saveField(FieldDTO fieldDTO);
    void updateField(String id,FieldDTO fieldDTO);
    void deleteField(String id);
    FieldResponse getSelectedField(String id);
    List<FieldDTO> getAllFields();
}
