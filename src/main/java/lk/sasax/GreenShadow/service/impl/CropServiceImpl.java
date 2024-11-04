package lk.sasax.GreenShadow.service.impl;

import lk.sasax.GreenShadow.customObj.CropResponse;
import lk.sasax.GreenShadow.dto.impl.CropDTO;
import lk.sasax.GreenShadow.entity.impl.Crop;
import lk.sasax.GreenShadow.entity.impl.Field;
import lk.sasax.GreenShadow.exception.DataPersistFailedException;
import lk.sasax.GreenShadow.exception.FieldNotFoundException;
import lk.sasax.GreenShadow.exception.VehicleNotFoundException;
import lk.sasax.GreenShadow.repository.CropRepository;
import lk.sasax.GreenShadow.repository.FieldRepository;
import lk.sasax.GreenShadow.service.CropService;
import lk.sasax.GreenShadow.util.AppUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CropServiceImpl implements CropService {

    private final CropRepository cropRepository;
    private ModelMapper modelMapper;
    @Autowired
    private FieldRepository fieldRepository;

    public CropServiceImpl(CropRepository cropRepository, ModelMapper modelMapper) {
        this.cropRepository = cropRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void saveCrop(CropDTO cropDTO) {

        // Find the Field entity based on fieldCode
        Field field = fieldRepository.findById(cropDTO.getFieldCode())
                .orElseThrow(() -> new FieldNotFoundException("Field not found with code: " + cropDTO.getFieldCode()));

        // Map CropDTO to Crop entity
        Crop crop = modelMapper.map(cropDTO, Crop.class);
        crop.setField(field);

        // Save the Crop entity
        cropDTO.setCropCode(AppUtil.createCropId());
        Crop savedCrop = cropRepository.save(crop);
        if (savedCrop == null) {
            throw new DataPersistFailedException("Crop not saved");
        }
    }

    @Override
    public void updateCrop(String id, CropDTO cropDTO) {

    }

    @Override
    public void deleteCrop(String id) {

    }

    @Override
    public CropResponse getSelectedCrop(String id) {
        return null;
    }

    @Override
    public List<CropDTO> getAllCrops() {
        return List.of();
    }
}
