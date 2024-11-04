package lk.sasax.GreenShadow.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lk.sasax.GreenShadow.dto.impl.CropDTO;
import lk.sasax.GreenShadow.entity.impl.Field;
import lk.sasax.GreenShadow.exception.DataPersistFailedException;
import lk.sasax.GreenShadow.repository.FieldRepository;
import lk.sasax.GreenShadow.service.CropService;
import lk.sasax.GreenShadow.util.AppUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/crop")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CropController {
    @Autowired
    private final CropService cropService;
    private static final Logger logger = LoggerFactory.getLogger(CropController.class);

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> saveCrop(
            @RequestPart("cropCommonName") String cropCommonName,
            @RequestPart("cropScientificName") String cropScientificName,
            @RequestPart(value = "cropImage",required = false) MultipartFile cropImage,
            @RequestPart("category") String category,
            @RequestPart("cropSeason") String cropSeason,
            @RequestPart("fieldCode") String fieldCode
    ) {
        try {
            String cropImageBase64 = cropImage != null ? AppUtil.toBase64(cropImage) : null;

            // Build CropDTO with the provided request parts
            CropDTO buildCropDTO = new CropDTO();
            buildCropDTO.setCropCommonName(cropCommonName);
            buildCropDTO.setCropScientificName(cropScientificName);
            buildCropDTO.setCropImage(cropImageBase64);
            buildCropDTO.setCategory(category);
            buildCropDTO.setCropSeason(cropSeason);
            buildCropDTO.setFieldCode(fieldCode);

            cropService.saveCrop(buildCropDTO);

            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (DataPersistFailedException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
