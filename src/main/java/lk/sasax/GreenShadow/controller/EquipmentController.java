package lk.sasax.GreenShadow.controller;

import lk.sasax.GreenShadow.customObj.EquipmentResponse;
import lk.sasax.GreenShadow.dto.impl.EquipmentDTO;
import lk.sasax.GreenShadow.exception.DataPersistFailedException;
import lk.sasax.GreenShadow.exception.EquipmentNotFoundException;
import lk.sasax.GreenShadow.exception.StaffNotFoundException;
import lk.sasax.GreenShadow.service.EquipmentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/equipment")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EquipmentController {
    @Autowired
    private final EquipmentService equipmentService;

    private static final Logger logger = LoggerFactory.getLogger(EquipmentController.class);

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveEquipment(@RequestBody EquipmentDTO equipmentDTO) {
        try{
            System.out.println(equipmentDTO);
            equipmentService.saveEquipment(equipmentDTO);
            logger.info("Equipment saved successfully");
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (DataPersistFailedException e){
            e.printStackTrace();
            logger.error("Request failed with status: BAD_REQUEST due to invalid input or processing error");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }catch (Exception e){
            e.printStackTrace();
            logger.error("Cannot save equipment");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //200
    //OK But not Updated
    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateEquipment(@PathVariable("id") String id, @RequestBody EquipmentDTO equipmentDTO) {
        try{
            equipmentService.updateEquipment(id,equipmentDTO);
            logger.info("Equipment updated successfully");
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch (EquipmentNotFoundException e){
            logger.error("Equipment not found");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (DataPersistFailedException e){
            logger.error("Request failed with status: BAD_REQUEST due to invalid input or processing error");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }catch (Exception e){
            logger.error("Cannot update equipment");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteEquipment(@PathVariable("id") String id) {
        try{
            equipmentService.deleteEquipment(id);
            logger.info("Equipment deleted successfully");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch (IllegalStateException e){
            logger.error("Request failed with status: BAD_REQUEST due to invalid input or processing error");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }catch (StaffNotFoundException e){
            logger.error("Equipment not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (Exception e){
            logger.error("Cannot delete equipment");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EquipmentResponse getSelectedEquipment(@PathVariable("id") String id) {
        try{
            EquipmentResponse equipmentResponse = equipmentService.getSelectedEquipment(id);
            logger.info("Equipment selected successfully");
            return ResponseEntity.status(HttpStatus.OK).body(equipmentResponse).getBody();
        }catch (EquipmentNotFoundException e){
            logger.error("Equipment not found");
            return (EquipmentResponse) ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (Exception e){
            logger.error("Cannot select equipment");
            return (EquipmentResponse) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EquipmentDTO>> getAllEquipment() {
        try{
            List<EquipmentDTO> equipments = equipmentService.getAllEquipments();
            logger.info("Equipment list selected successfully");
            return ResponseEntity.status(HttpStatus.OK).body(equipments);
        }catch (EquipmentNotFoundException e){
            logger.error("Equipment not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (Exception e){
            logger.error("Cannot select equipments");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
