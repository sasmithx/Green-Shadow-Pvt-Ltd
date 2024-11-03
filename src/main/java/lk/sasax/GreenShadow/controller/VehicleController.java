package lk.sasax.GreenShadow.controller;

import lk.sasax.GreenShadow.customObj.VehicleResponse;
import lk.sasax.GreenShadow.dto.impl.VehicleDTO;
import lk.sasax.GreenShadow.exception.DataPersistFailedException;
import lk.sasax.GreenShadow.exception.VehicleNotFoundException;
import lk.sasax.GreenShadow.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/vehicle")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class VehicleController {
    private final VehicleService vehicleService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveVehicle(@RequestBody VehicleDTO vehicleDTO) {
        try{
            vehicleService.saveVehicle(vehicleDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (DataPersistFailedException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public VehicleResponse getSelectedVehicle(@PathVariable("id") String id) {
        try{
            VehicleResponse vehicleResponse = vehicleService.getSelectedVehicle(id);
            return ResponseEntity.status(HttpStatus.OK).body(vehicleResponse).getBody();
        }catch (VehicleNotFoundException e){
            return (VehicleResponse) ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (Exception e){
            return (VehicleResponse) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<VehicleDTO>> getAllVehicles() {
        try{
            List<VehicleDTO> vehicles = vehicleService.getAllVehicles();
            return ResponseEntity.status(HttpStatus.OK).body(vehicles);
        } catch (VehicleNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateVehicle(@PathVariable("id") String id, @RequestBody VehicleDTO vehicleDTO) {
        try{
            vehicleService.updateVehicle(id, vehicleDTO);
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch (VehicleNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (DataPersistFailedException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable("id") String id) {
        try{
            vehicleService.deleteVehicle(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (IllegalStateException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }catch (VehicleNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
