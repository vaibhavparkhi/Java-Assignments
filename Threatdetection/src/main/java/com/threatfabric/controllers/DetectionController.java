package com.threatfabric.controllers;

import com.threatfabric.dto.DetectionRequest;
import com.threatfabric.entities.Detection;
import com.threatfabric.entities.Device;
import com.threatfabric.exceptions.DetectionNotFoundException;
import com.threatfabric.exceptions.DeviceNotFoundException;
import com.threatfabric.repositories.DetectionRepository;
import com.threatfabric.repositories.DeviceRepository;
import com.threatfabric.services.DetectionService;
import com.threatfabric.utils.UtilsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class DetectionController {

    @Autowired
    private DetectionService detectionService;
    
    @GetMapping("/v1/detections")
    public ResponseEntity fetchAllDetections() {
        List<Detection> sortedDetection = detectionService.findAllDetections();
        if(sortedDetection.size()> 0){
            return new ResponseEntity(sortedDetection, HttpStatus.OK);
        }else{
            throw new DetectionNotFoundException("Detection(s) not found!");
        }
    }

    /**
     * @apiNote Its returns detections by Id or api error
     * @param detectionId
     * @return ResponseEntity with Success or Error
     */
    @GetMapping("/v1/detections/{detectionId}")
    public ResponseEntity findDetectionById(@PathVariable @NotBlank Long detectionId) {
        Optional<Detection> optDetection = detectionService.findDetectionById(detectionId);
        if (optDetection.isPresent()) {
            return new ResponseEntity(optDetection.get(), HttpStatus.OK);
        } else {
            throw new DetectionNotFoundException("Detection with Id " + detectionId + " not found!");
        }
    }


    /**
     * @apiNote Its returns the detection from date to date or api error
     * @param from
     * @param to
     * @return ResponseEntity with Success (OK) or DetectionNotFoundException (404) excepetion
     */
    @GetMapping("/v1/detections/fromto/{from}/{to}")
    public ResponseEntity findDetectionByDates(@PathVariable @NotBlank Long from, @PathVariable @NotBlank Long to) {
        List<Detection> filteredListByDate = detectionService.findDetectionBetweenDates(from, to);
        if (filteredListByDate.size() > 0) {
            return new ResponseEntity(filteredListByDate, HttpStatus.OK);
        } else {
            throw new DetectionNotFoundException("Detections from date " + UtilsManager.convertEpochToLocalDateTime(from) + " to " + UtilsManager.convertEpochToLocalDateTime(to) + " not found!");
        }
    }


    /**
     * @apiNote Its returns detections by app type or error
     * @param byapptype
     * @return ResponseEntity with Success or Error
     */
    @GetMapping("/v1/detections/byapptype/{byapptype}")
    public ResponseEntity findByNameOfApp(@PathVariable @NotBlank String byapptype) {
        List<Detection> detections = detectionRepository.findByTypeOfApp(byapptype);
        if (detections.size() > 0) {
            return new ResponseEntity(detections, HttpStatus.OK);
        } else {
            throw new DetectionNotFoundException("Detection with App Name " + byapptype + " not found!");
        }

    }

    /**
     * @apiNote Its save the detection for particular device id
     * @param detectionReq
     * @return ResponseEntity with Success or Error
     */
    @PostMapping("/v1/detections")
    public ResponseEntity saveDetections(@RequestBody DetectionRequest detectionReq) {
        Optional<Device> deviceOpt = deviceRepository.findById(detectionReq.getDeviceId());
        if (deviceOpt.isPresent()) {
            Device device = deviceOpt.get();
            deviceRepository.save(device);
            Detection detection1 = new Detection(detectionReq.getDetection().getTime(),
                    detectionReq.getDetection().getNameOfApp(), detectionReq.getDetection().getTypeOfApp(),
                    detectionReq.getDetection().getDetectionType());

            detection1.setDevice(device);
            detection1.setDeleted(false);
            device.addDetection(detection1);
            detectionRepository.save(detection1);
        }else{
            throw new DeviceNotFoundException("Device with Id "+ detectionReq.getDeviceId() + " not found!");
        }
        return new ResponseEntity("Added Detection Successfully!", HttpStatus.OK);
    }

    @PutMapping("/v1/detections/{deviceId}/{detectionId}")
    public ResponseEntity updateDetection(@PathVariable @NotBlank Long deviceId, @PathVariable @NotBlank Long detectionId) {

    }

    @DeleteMapping("/v1/detections/{deviceId}/{detectionId}")
    public ResponseEntity deleteDetectionById(@PathVariable @NotBlank Long deviceId, @PathVariable @NotBlank Long detectionId) {

    }

    @DeleteMapping("/v1/detections/{deviceId}")
    public ResponseEntity deleteAllDetectionOfDevice@PathVariable @NotBlank Long deviceId, @PathVariable @NotBlank Long detectionId) {

    }


}


