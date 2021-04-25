package com.threatfabric.services;

import com.threatfabric.entities.Detection;

import java.util.List;
import java.util.Optional;

public interface DetectionService {

    public List<Detection> findAllDetections();
    public Optional<Detection> findDetectionById(Long detectionId);
    public List<Detection> findDetectionBetweenDates(Long from, Long to);
    public List<Detection> findDetectionsByAppType(String byapptype);
    public void saveDetections(DetectionRequest detectionReq);
    public void updateDetection(Long deviceId, Long detectionId);
    public void deleteDetectionById(Long detectionId);
    public void deleteAllDetectionOfDevice(Long deviceId);
}
