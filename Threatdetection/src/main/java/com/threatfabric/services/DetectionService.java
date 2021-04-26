package com.threatfabric.services;

import com.threatfabric.dto.DetectionRequest;
import com.threatfabric.entities.Detection;

import java.util.List;
import java.util.Optional;

public interface DetectionService {

    public List<Detection> findAllDetections();
    public List<Detection> fetchAllDetectionsByDeviceId(Long deviceId);
    public Optional<Detection> findDetectionById(Long deviceId, Long detectionId);
    public List<Detection> findDetectionBetweenDates(Long from, Long to);
    public List<Detection> findDetectionsByAppType(String byapptype);
    public void saveDetections(DetectionRequest detectionReq);
    public void resolvedDetection(Long deviceId, Long detectionId);
    public void clearDetectionById(Long deviceId, Long detectionId);
    public void clearAllDetectionOfDevice(Long deviceId);
}
