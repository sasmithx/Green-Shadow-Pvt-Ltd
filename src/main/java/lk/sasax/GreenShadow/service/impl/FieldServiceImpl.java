package lk.sasax.GreenShadow.service.impl;

import lk.sasax.GreenShadow.customObj.FieldResponse;
import lk.sasax.GreenShadow.dto.impl.CropDTO;
import lk.sasax.GreenShadow.dto.impl.FieldDTO;
import lk.sasax.GreenShadow.entity.impl.Field;
import lk.sasax.GreenShadow.exception.DataPersistFailedException;
import lk.sasax.GreenShadow.repository.FieldRepository;
import lk.sasax.GreenShadow.service.FieldService;
import lk.sasax.GreenShadow.util.AppUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FieldServiceImpl implements FieldService {
    private final FieldRepository fieldRepository;
    private final ModelMapper modelMapper;


    @Override
    public void saveField(FieldDTO fieldDTO) {
        fieldDTO.setFCode(AppUtil.createFieldId());
        Field savedField = fieldRepository.save(modelMapper.map(fieldDTO, Field.class));
        if(savedField == null) {
            throw new DataPersistFailedException("Field not saved");
        }
    }

    @Override
    public void updateField(String id, FieldDTO fieldDTO) {

    }

    @Override
    public void deleteField(String id) {

    }

    @Override
    public FieldResponse getSelectedField(String id) {
        return null;
    }

    @Override
    public List<FieldDTO> getAllFields() {
        return List.of();
    }
}
