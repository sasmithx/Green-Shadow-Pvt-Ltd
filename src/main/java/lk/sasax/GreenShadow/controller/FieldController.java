package lk.sasax.GreenShadow.controller;

import lk.sasax.GreenShadow.customObj.CropResponse;
import lk.sasax.GreenShadow.customObj.FieldResponse;
import lk.sasax.GreenShadow.dto.impl.FieldDTO;
import lk.sasax.GreenShadow.entity.impl.Crop;
import lk.sasax.GreenShadow.entity.impl.Equipment;
import lk.sasax.GreenShadow.entity.impl.MonitoringLog;
import lk.sasax.GreenShadow.entity.impl.Staff;
import lk.sasax.GreenShadow.exception.CropNotFoundException;
import lk.sasax.GreenShadow.exception.DataPersistFailedException;
import lk.sasax.GreenShadow.exception.FieldNotFoundException;
import lk.sasax.GreenShadow.service.FieldService;
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
@RequestMapping("api/v1/field")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class FieldController {
    @Autowired
    private final FieldService fieldService;

    private static final Logger logger = LoggerFactory.getLogger(FieldController.class);

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> saveField(
            //@RequestPart("fCode") String fCode,
            @RequestPart("fieldName") String fieldName,
            @RequestPart("fieldSize") String fieldSize,
            @RequestPart(value = "fieldLocation",required = false) String fieldLocation,
            @RequestPart(value = "fieldImage1",required = false) MultipartFile fieldImage1,
            @RequestPart(value = "fieldImage2",required = false) MultipartFile fieldImage2,
            @RequestParam("status") AvailabilityStatus status,
            @RequestParam(value = "crops",required = false) List<Crop> crops,
            @RequestParam(value = "staffs",required = false) List<Staff> staffs,
            @RequestParam(value = "equipments",required = false) List<Equipment> equipments,
            @RequestParam(value = "monitoringLogs",required = false) List<MonitoringLog> monitoringLogs
    ){
        try{
            String fieldPic = fieldImage1 !=null ? AppUtil.toBase64(fieldImage1) : null;
            String fieldPic1 = fieldImage2 != null ? AppUtil.toBase64(fieldImage2) : null;
            //fieldService.saveField(new FieldDTO(null,fieldName,fieldSize,fieldLocation,fieldPic,fieldPic1,status,crops,staffs,equipments,monitoringLogs));

            FieldDTO buildFieldDTO = new FieldDTO();

            buildFieldDTO.setFieldName(fieldName);
            buildFieldDTO.setFieldSize(Double.valueOf(fieldSize));
            buildFieldDTO.setFieldLocation(AppUtil.convertToPoint(fieldLocation));
            buildFieldDTO.setFieldImage1(fieldPic);
            buildFieldDTO.setFieldImage2(fieldPic1);
            buildFieldDTO.setStatus(status);
            buildFieldDTO.setCrops(crops);
            buildFieldDTO.setStaffs(staffs);
            buildFieldDTO.setEquipments(equipments);
            buildFieldDTO.setMonitoringLogs(monitoringLogs);
            fieldService.saveField(buildFieldDTO);
            logger.info("Save Field successful");
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (DataPersistFailedException e){
            e.printStackTrace();
            logger.error("Request failed with status: BAD_REQUEST due to invalid input or processing error");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }catch (Exception e){
            e.printStackTrace();
            logger.error("Request failed with status: INTERNAL_SERVER_ERROR due to internal server error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    private ResponseEntity<Void> updateField(

            @RequestParam ("fCode") String fCode,
            @RequestPart("fieldName") String fieldName,
            @RequestPart("fieldSize") String fieldSize,
            @RequestPart(value = "fieldLocation",required = false) String fieldLocation,
            @RequestPart(value = "fieldImage1",required = false) MultipartFile fieldImage1,
            @RequestPart(value = "fieldImage2",required = false) MultipartFile fieldImage2,
            @RequestParam("status")AvailabilityStatus status,
            @RequestPart(value = "crops",required = false) List<Crop> crops,
            @RequestPart(value = "staffs",required = false) List<Staff> staffs,
            @RequestPart(value = "equipments",required = false) List<Equipment> equipments,
            @RequestPart(value = "monitoringLogs",required = false) List<MonitoringLog> monitoringLogs
    )
    {
        try {
            String fieldPic = fieldImage1 != null ? AppUtil.toBase64(fieldImage1) : null;
            String fieldPic1 = fieldImage2 != null ? AppUtil.toBase64(fieldImage2) : null;
//            fieldService.saveField(new FieldDTO(null,fieldName,fieldSize,fieldLocation,fieldPic,fieldPic1,status,crops,staffs,equipments,monitoringLogs));

            FieldDTO buildFieldDTO = new FieldDTO();

            buildFieldDTO.setFCode(fCode);
            buildFieldDTO.setFieldName(fieldName);
            buildFieldDTO.setFieldSize(Double.valueOf(fieldSize));
            buildFieldDTO.setFieldLocation(AppUtil.convertToPoint(fieldLocation));
            buildFieldDTO.setFieldImage1(fieldPic);
            buildFieldDTO.setFieldImage2(fieldPic1);
            buildFieldDTO.setStatus(status);
            buildFieldDTO.setCrops(crops);
            buildFieldDTO.setStaffs(staffs);
            buildFieldDTO.setEquipments(equipments);
            buildFieldDTO.setMonitoringLogs(monitoringLogs);
            fieldService.saveField(buildFieldDTO);
            logger.info("Update Field successful");
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (DataPersistFailedException e) {
            e.printStackTrace();
            logger.error("Request failed with status: BAD_REQUEST due to invalid input or processing error");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Request failed with status: INTERNAL_SERVER_ERROR due to internal server error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteField(@PathVariable("id") String id){
        try{
            fieldService.deleteField(id);
            logger.info("Delete Field successful");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch (IllegalStateException e){
            logger.error("Request failed with status: BAD_REQUEST due to invalid input or processing error");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }catch (FieldNotFoundException e){
            logger.error("Field Not Found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (Exception e){
            logger.error("Request failed with status: INTERNAL_SERVER_ERROR due to internal server error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public FieldResponse getSelectedField(@PathVariable("id") String id){
        try {
            FieldResponse fieldResponse = fieldService.getSelectedField(id);
            logger.info("Get Field successful");
            return ResponseEntity.status(HttpStatus.OK).body(fieldResponse).getBody();
        }catch (FieldNotFoundException e){
            logger.error("Field Not Found");
            return (FieldResponse) ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (Exception e){
            logger.error("Request failed with status: INTERNAL_SERVER_ERROR due to internal server error");
            return (FieldResponse) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FieldDTO>> getAllFields(){
        try{
            List<FieldDTO> fields = fieldService.getAllFields();
            logger.info("Get Fields successful");
            return ResponseEntity.status(HttpStatus.OK).body(fields);
        }catch (FieldNotFoundException e){
            logger.error("Field Not Found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (Exception e){
            logger.error("Request failed with status: INTERNAL_SERVER_ERROR due to internal server error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
