package lk.sasax.GreenShadow.controller;

import lk.sasax.GreenShadow.customObj.MonitorLogResponse;
import lk.sasax.GreenShadow.dto.impl.MonitoringLogDTO;
import lk.sasax.GreenShadow.entity.impl.Crop;
import lk.sasax.GreenShadow.entity.impl.Field;
import lk.sasax.GreenShadow.entity.impl.Staff;
import lk.sasax.GreenShadow.exception.DataPersistFailedException;
import lk.sasax.GreenShadow.exception.LogNotFoundException;
import lk.sasax.GreenShadow.service.MonitoringLogService;
import lk.sasax.GreenShadow.util.AppUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/v1/log")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MonitoringLogController {
    private final MonitoringLogService monitoringLogService;


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> saveMLog(
            @RequestPart("logDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date logDate,
            @RequestPart("observation") String observation,
            @RequestPart(value = "observedImage", required = false) MultipartFile observedImage,
            @RequestPart(value = "fields",required = false) List<Field> fields,
            @RequestPart(value = "crops",required = false) List<Crop> crops,
            @RequestPart(value = "staffs",required = false) List<Staff> staffs
    )
    {
        try{
            String observedPic = observedImage != null ? AppUtil.toBase64(observedImage) : null;
            monitoringLogService.saveMLog(new MonitoringLogDTO(null,logDate,observation,observedPic,fields,crops,staffs));
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (DataPersistFailedException e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateMLog(
            @RequestPart("logCode") String logCode,
            @RequestPart("logDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date logDate,
            @RequestPart("observation") String observation,
            @RequestPart(value = "observedImage", required = false) MultipartFile observedImage,
            @RequestParam(value = "fields",required = false) List<Field> fields,
            @RequestParam(value = "crops",required = false) List<Crop> crops,
            @RequestParam(value = "staffs",required = false) List<Staff> staffs
    )
    {
        try{
            String observedPic = observedImage != null ? AppUtil.toBase64(observedImage) : null;
            monitoringLogService.saveMLog(new MonitoringLogDTO(logCode,logDate,observation,observedPic,fields,crops,staffs));
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch (LogNotFoundException e){
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
    public ResponseEntity<Void> deleteMLog(@PathVariable("id") String id){
        try{
            monitoringLogService.deleteMLog(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch (IllegalStateException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }catch (LogNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MonitorLogResponse getSelectedMLog(@PathVariable("id") String id){
        try{
            MonitorLogResponse monitorLogResponse = monitoringLogService.getSelectedMLog(id);
            return ResponseEntity.status(HttpStatus.OK).body(monitorLogResponse).getBody();
        }catch (LogNotFoundException e){
            return (MonitorLogResponse) ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (Exception e){
            return (MonitorLogResponse) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MonitoringLogDTO>> getAllLogs(){
        try{
            List<MonitoringLogDTO> logs = monitoringLogService.getAllMLogs();
            return ResponseEntity.status(HttpStatus.OK).body(logs);
        }catch (LogNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
