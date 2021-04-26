package com.threatfabric.controllers;

import com.threatfabric.dto.DetectionRequest;
import com.threatfabric.entities.Detection;
import com.threatfabric.exceptions.DetectionNotFoundException;
import com.threatfabric.services.DetectionService;
import com.threatfabric.utils.UtilsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;

@RestController
public class DetectionController {

    @Autowired
    private DetectionService detectionService;

    /**
     * @return ResponseEntity with Success or Error
     * @apiNote API return List of all detections irrespective of device
     */
    @GetMapping("/v1/detections")
    public ResponseEntity fetchAllDetections() {
        List<Detection> sortedDetection = detectionService.findAllDetections();
        if (sortedDetection.size() > 0) {
            return new ResponseEntity(sortedDetection, HttpStatus.OK);
        } else {
            throw new DetectionNotFoundException("Detection(s) not found!");
        }
    }

    /**
     * @param deviceId
     * @return ResponseEntity with Success or Error
     * @apiNote API return list of all detections respective of device id
     */
    @GetMapping("/v1/detections/{deviceId}")
    public ResponseEntity fetchAllDetectionsByDeviceId(@PathVariable Long deviceId) {
        List<Detection> sortedDetection = detectionService.fetchAllDetectionsByDeviceId(deviceId);
        if (sortedDetection.size() > 0) {
            return new ResponseEntity(sortedDetection, HttpStatus.OK);
        } else {
            throw new DetectionNotFoundException("Detection(s) not found!");
        }
    }

    /**
     * @param deviceId
     * @param detectionId
     * @return ResponseEntity with Success or Error
     * @apiNote Its returns detections by Id or api error
     */
    @GetMapping("/v1/detections/{deviceId}/{detectionId}")
    public ResponseEntity findDetectionById(@PathVariable @NotBlank Long deviceId, @PathVariable @NotBlank Long detectionId) {
        Optional<Detection> optDetection = detectionService.findDetectionById(deviceId, detectionId);
        if (optDetection.isPresent()) {
            return new ResponseEntity(optDetection.get(), HttpStatus.OK);
        } else {
            throw new DetectionNotFoundException("Detection with Id " + detectionId + " not found!");
        }
    }


    /**
     * @param from
     * @param to
     * @return ResponseEntity with Success (OK) or DetectionNotFoundException (404) excepetion
     * @apiNote Its returns the detection from date to date or api error
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
     * @param byapptype
     * @return ResponseEntity with Success or Error
     * @apiNote Its returns detections by app type or error
     */
    @GetMapping("/v1/detections/byapptype/{byapptype}")
    public ResponseEntity findByNameOfApp(@PathVariable @NotBlank String byapptype) {
        List<Detection> detections = detectionService.findDetectionsByAppType(byapptype);
        if (detections.size() > 0) {
            return new ResponseEntity(detections, HttpStatus.OK);
        } else {
            throw new DetectionNotFoundException("Detection with App Name " + byapptype + " not found!");
        }

    }

    /**
     * @param detectionReq
     * @return ResponseEntity with Success or Error
     * @apiNote Its save the detection for particular device id
     */
    @PostMapping("/v1/detections")
    public ResponseEntity saveDetections(@RequestBody DetectionRequest detectionReq) {
        detectionService.saveDetections(detectionReq);
        return new ResponseEntity("Added detection successfully!", HttpStatus.OK);
    }

    /**
     *
     * @param deviceId
     * @param detectionId
     * @return ResponseEntity with successful update of detection
     * @apiNote It update the resolved detection on succesful transaction
     */
    @PutMapping("/v1/detections/{deviceId}/{detectionId}")
    public ResponseEntity resolvedDetection(@PathVariable @NotBlank Long deviceId, @PathVariable @NotBlank Long detectionId) {
        detectionService.resolvedDetection(deviceId, detectionId);
        return new ResponseEntity("Updated detection successfully!", HttpStatus.OK);
    }

    /**
     *
     * @param deviceId
     * @param detectionId
     * @return ResponseEntity with successful delete of detection
     * @apiNote It delete the already resolved detection on succesful transaction
     */
    @DeleteMapping("/v1/detections/{deviceId}/{detectionId}")
    public ResponseEntity clearDetectionById(@PathVariable @NotBlank Long deviceId, @PathVariable @NotBlank Long detectionId) {
        detectionService.clearDetectionById(deviceId, detectionId);
        return new ResponseEntity("Deleted detection successfully!", HttpStatus.OK);
    }

    /**
     *
     * @param deviceId
     * @param detectionId
     * @return ResponseEntity with successful delete of detection
     * @apiNote It delete all resolved detections on succesful transaction
     */
    @DeleteMapping("/v1/detections/{deviceId}")
    public ResponseEntity clearAllDetectionOfDevice(@PathVariable @NotBlank Long deviceId) {
        detectionService.clearAllDetectionOfDevice(deviceId);
        return new ResponseEntity("Cleared all detections of device id "+ deviceId +" successfully!", HttpStatus.OK);
    }

}


