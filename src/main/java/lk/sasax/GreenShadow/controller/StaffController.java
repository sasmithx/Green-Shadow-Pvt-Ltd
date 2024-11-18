package lk.sasax.GreenShadow.controller;

import lk.sasax.GreenShadow.customObj.StaffResponse;
import lk.sasax.GreenShadow.dto.impl.StaffDTO;
import lk.sasax.GreenShadow.exception.DataPersistFailedException;
import lk.sasax.GreenShadow.exception.StaffNotFoundException;
import lk.sasax.GreenShadow.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/staff")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class StaffController {
    private final StaffService staffService;
    private static final Logger logger = LoggerFactory.getLogger(StaffController.class);

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveStaff(@RequestBody StaffDTO staffDTO) {
        try{
            staffService.saveStaff(staffDTO);
            logger.info("staff saved successfully");
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (DataPersistFailedException e){
            logger.error("Request failed with status: BAD_REQUEST due to invalid input or processing error");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }catch (Exception e){
            logger.error("Request failed with status: INTERNAL_SERVER_ERROR due to internal server error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public StaffResponse getSelectedVehicle(@PathVariable("id") String id){
        try{
            StaffResponse staffResponse = staffService.getSelectedStaff(id);
            logger.info("staff selected successfully");
            return ResponseEntity.status(HttpStatus.OK).body(staffResponse).getBody();
        }catch (StaffNotFoundException e){
            logger.error("staff not found");
            return (StaffResponse) ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            logger.error("Request failed with status: INTERNAL_SERVER_ERROR due to internal server error");
            return (StaffResponse) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StaffDTO>> getAllStaff(){
        try{
            List<StaffDTO> staffList = staffService.getAllStaff();
            logger.info("staff list retrieved");
            return ResponseEntity.status(HttpStatus.OK).body(staffList);
        }catch (StaffNotFoundException e){
            logger.error("staff not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (Exception e){
            logger.error("Request failed with status: INTERNAL_SERVER_ERROR due to internal server error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateStaff(@PathVariable("id") String id,@RequestBody StaffDTO staffDTO){
        try{
            staffService.updateStaff(id, staffDTO);
            logger.info("staff updated successfully");
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch (StaffNotFoundException e){
            logger.error("staff not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (DataPersistFailedException e){
            logger.error("Request failed with status: BAD_REQUEST due to invalid input or processing error");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }catch (Exception e){
            logger.error("Request failed with status: INTERNAL_SERVER_ERROR due to internal server error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteStaff(@PathVariable("id") String id){
        try{
            staffService.deleteStaff(id);
            logger.info("staff deleted successfully");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch (IllegalStateException e){
            logger.error("Request failed with status: BAD_REQUEST due to invalid input or processing error");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }catch (StaffNotFoundException e){
            logger.error("staff not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (Exception e){
            logger.error("Request failed with status: INTERNAL_SERVER_ERROR due to internal server error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
