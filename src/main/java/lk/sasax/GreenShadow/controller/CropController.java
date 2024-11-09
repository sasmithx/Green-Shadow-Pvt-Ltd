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

    //415
    //Unsupported Media Type
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> saveCrop(
            @RequestPart("commonName") String commonName,
            @RequestPart("scientificName") String scientificName,
            @RequestPart("category") String category,
            @RequestPart("season") String season,
            @RequestPart("status") AvailabilityStatus status,
            @RequestPart(value = "cropImage", required = false) MultipartFile cropImage,
            @RequestPart(value = "field",required = false) Field field,
            @RequestPart(value = "monitoringLogs",required = false) List<MonitoringLog> monitoringLogs
    ){
        try{
            String cropPic = cropImage != null ? AppUtil.toBase64(cropImage) : null;
            cropService.saveCrop(new CropDTO(null,commonName,scientificName,category,season,status,cropPic,field,monitoringLogs));
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (DataPersistFailedException e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //Bad Request
    @PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    private ResponseEntity<Void> updateCrop(
            @RequestPart("cropCode") String cropCode,
            @RequestPart("commonName") String commonName,
            @RequestPart("scientificName") String scientificName,
            @RequestPart("category") String category,
            @RequestPart("season") String season,
            @RequestPart("status") AvailabilityStatus status,
            @RequestPart(value = "cropImage", required = false) MultipartFile cropImage,
            @RequestPart(value = "field",required = false) Field field,
            @RequestPart(value = "monitoringLogs",required = false) List<MonitoringLog> monitoringLogs
    ){
        try{
            String cropPic = cropImage != null ? AppUtil.toBase64(cropImage) : null;
            cropService.updateCrop(new CropDTO(cropCode,commonName,scientificName,category,season,status,cropPic,field,monitoringLogs));
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch (CropNotFoundException e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (DataPersistFailedException e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteCrop(@PathVariable("id") String id){
        try{
            cropService.deleteCrop(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch (IllegalStateException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }catch (CropNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CropResponse getSelectedCrop(@PathVariable("id") String id){
        try {
            CropResponse cropResponse = cropService.getSelectedCrop(id);
            return ResponseEntity.status(HttpStatus.OK).body(cropResponse).getBody();
        }catch (CropNotFoundException e){
            return (CropResponse) ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (Exception e){
            return (CropResponse) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CropDTO>> getAllCrop(){
        try{
            List<CropDTO> crops = cropService.getAllCrops();
            return ResponseEntity.status(HttpStatus.OK).body(crops);
        }catch (CropNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
