package lk.sasax.GreenShadow.controller;

import lk.sasax.GreenShadow.customObj.CropResponse;
import lk.sasax.GreenShadow.dto.impl.CropDTO;
import lk.sasax.GreenShadow.entity.impl.Field;
import lk.sasax.GreenShadow.entity.impl.MonitoringLog;
import lk.sasax.GreenShadow.exception.CropNotFoundException;
import lk.sasax.GreenShadow.exception.DataPersistFailedException;
import lk.sasax.GreenShadow.service.CropService;
import lk.sasax.GreenShadow.util.AppUtil;
import lk.sasax.GreenShadow.util.Enum.AvailabilityStatus;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
            @RequestPart("commonName") String commonName,
            @RequestPart("scientificName") String scientificName,
            @RequestPart("category") String category,
            @RequestPart("season") String season,
            @RequestParam("status") AvailabilityStatus status,
            @RequestPart(value = "cropImage", required = false) MultipartFile cropImage,
            @RequestParam(value = "field",required = false) Field field,
            @RequestPart(value = "monitoringLogs",required = false) List<MonitoringLog> monitoringLogs
    ){
        try{
            logger.info("Request received to save a new crop");
            //System.out.println("Save CROP");
            String cropPic = cropImage != null ? AppUtil.toBase64(cropImage) : null;
            cropService.saveCrop(new CropDTO(null,commonName,scientificName,category,season,status,cropPic,field,monitoringLogs));
            logger.info("Crop saved successfully");
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (DataPersistFailedException e){
            e.printStackTrace();
            logger.error("DataPersist failed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }catch (Exception e){
            e.printStackTrace();
            logger.error("Crop failed");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    private ResponseEntity<Void> updateCrop(
            @RequestPart("cropCode") String cropCode,
            @RequestPart("commonName") String commonName,
            @RequestPart("scientificName") String scientificName,
            @RequestPart("category") String category,
            @RequestPart("season") String season,
            @RequestParam("status") AvailabilityStatus status,
            @RequestPart(value = "cropImage", required = false) MultipartFile cropImage,
            @RequestParam(value = "field",required = false) Field field,
            @RequestPart(value = "monitoringLogs",required = false) List<MonitoringLog> monitoringLogs
    ){
        try{
            String cropPic = cropImage != null ? AppUtil.toBase64(cropImage) : null;
            logger.info("Preparing to update crop with code: {}", cropCode);
            cropService.updateCrop(new CropDTO(cropCode,commonName,scientificName,category,season,status,cropPic,field,monitoringLogs));
            logger.info("Crop updated successfully with code: {}", cropCode);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch (CropNotFoundException e){
            e.printStackTrace();
            logger.error("Crop update failed - Crop with code {} not found", cropCode, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (DataPersistFailedException e){
            e.printStackTrace();
            logger.error("Crop update failed - Data persistence error for crop with code {}", cropCode, e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }catch (Exception e){
            logger.error("Unexpected error occurred during crop update for crop with code {}", cropCode, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteCrop(@PathVariable("id") String id){
        try{
            cropService.deleteCrop(id);
            logger.info("Crop deleted successfully");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch (IllegalStateException e){
            logger.error("Crop not found");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }catch (CropNotFoundException e){
            logger.error("Crop not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (Exception e){
            logger.error("Crop failed");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CropResponse getSelectedCrop(@PathVariable("id") String id){
        try {
            CropResponse cropResponse = cropService.getSelectedCrop(id);
            logger.info("Crop selected successfully");
            return ResponseEntity.status(HttpStatus.OK).body(cropResponse).getBody();
        }catch (CropNotFoundException e){
            logger.error("Crop not found");
            return (CropResponse) ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (Exception e){
            logger.error("Crop failed");
            return (CropResponse) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CropDTO>> getAllCrop(){
        try{
            List<CropDTO> crops = cropService.getAllCrops();
            logger.info("Crops found");
            return ResponseEntity.status(HttpStatus.OK).body(crops);
        }catch (CropNotFoundException e){
            logger.error("Crop not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (Exception e){
            logger.error("Crop failed");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
