package lk.sasax.GreenShadow.service.impl;

import lk.sasax.GreenShadow.customObj.FieldResponse;
import lk.sasax.GreenShadow.customObj.impl.FieldErrorResponse;
import lk.sasax.GreenShadow.dto.impl.FieldDTO;
import lk.sasax.GreenShadow.entity.impl.Field;
import lk.sasax.GreenShadow.exception.DataPersistFailedException;
import lk.sasax.GreenShadow.exception.FieldNotFoundException;
import lk.sasax.GreenShadow.repository.FieldRepository;
import lk.sasax.GreenShadow.service.FieldService;
import lk.sasax.GreenShadow.util.AppUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FieldServiceImpl implements FieldService {
    private final FieldRepository fieldRepository;
    private final ModelMapper modelMapper;


    @Override
    public void saveField(FieldDTO fieldDTO) {
        fieldDTO.setFCode(AppUtil.createFieldId());
        Field savedField =
                fieldRepository.save(modelMapper.map(fieldDTO, Field.class));
        if(savedField == null) {
            throw new DataPersistFailedException("Field not saved");
        }
    }

    @Override
    public void updateField(String id, FieldDTO fieldDTO) {

    }

    @Override
    public void deleteField(String id) {
        Optional<Field> selectedField = fieldRepository.findById(id);
        if(!selectedField.isPresent()) {
            throw new FieldNotFoundException("Field not found");
        }else fieldRepository.deleteById(id);
    }

    @Override
    public FieldResponse getSelectedField(String id) {
        Optional<Field> byId = fieldRepository.findById(id);
        return (byId.isPresent())
                ?modelMapper.map(byId.get(), FieldDTO.class)
                :new FieldErrorResponse(0,"Field not found");
    }

    @Override
    public List<FieldDTO> getAllFields() {
        return modelMapper.map(fieldRepository.findAll(), List.class);
    }
}
