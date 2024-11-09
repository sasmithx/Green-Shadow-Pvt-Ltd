package lk.sasax.GreenShadow.service.impl;

import jakarta.transaction.Transactional;
import lk.sasax.GreenShadow.customObj.CropResponse;
import lk.sasax.GreenShadow.customObj.impl.CropErrorResponse;
import lk.sasax.GreenShadow.dto.impl.CropDTO;
import lk.sasax.GreenShadow.entity.impl.Crop;
import lk.sasax.GreenShadow.exception.CropNotFoundException;
import lk.sasax.GreenShadow.exception.DataPersistFailedException;
import lk.sasax.GreenShadow.repository.CropRepository;
import lk.sasax.GreenShadow.service.CropService;
import lk.sasax.GreenShadow.util.AppUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CropServiceImpl implements CropService {

    private final CropRepository cropRepository;
    private final ModelMapper modelMapper;


    @Override
    public void saveCrop(CropDTO cropDTO) {
        cropDTO.setCropCode(AppUtil.createCropId());
        Crop savedCrop=
                cropRepository.save(modelMapper.map(cropDTO, Crop.class));
        if (savedCrop == null){
            throw new DataPersistFailedException("Crop not saved");
        }
    }

    @Override
    @Transactional
    public void updateCrop(CropDTO cropDTO) {
        Crop crop = cropRepository.findById(cropDTO.getCropCode())
                .orElseThrow(() -> new CropNotFoundException("Crop not found"));
        crop.setCommonName(cropDTO.getCommonName());
        crop.setScientificName(cropDTO.getScientificName());
        crop.setCategory(cropDTO.getCategory());
        crop.setSeason(cropDTO.getSeason());
        crop.setStatus(cropDTO.getStatus());
        crop.setCropImage(cropDTO.getCropImage());
        crop.setField(cropDTO.getField());
        crop.setMonitoringLogs(cropDTO.getMonitoringLogs());
    }

    @Override
    public void deleteCrop(String id) {
         Optional<Crop> selectedCrop=cropRepository.findById(id);
         if (!selectedCrop.isPresent()){
             throw new CropNotFoundException("Crop not found");
         }else cropRepository.deleteById(id);
    }

    @Override
    public CropResponse getSelectedCrop(String id) {
        Optional<Crop> byId = cropRepository.findById(id);
        return(byId.isPresent()
                ?modelMapper.map(byId.get(),CropDTO.class)
                :new CropErrorResponse(0,"Crop not found"));
    }

    @Override
    public List<CropDTO> getAllCrops() {
        return modelMapper.map(cropRepository.findAll(), List.class);
    }
}
