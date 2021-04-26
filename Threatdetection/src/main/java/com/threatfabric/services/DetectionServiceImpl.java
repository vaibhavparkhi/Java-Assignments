package com.threatfabric.services;

import com.threatfabric.dto.DetectionRequest;
import com.threatfabric.entities.Detection;
import com.threatfabric.entities.DetectionType;
import com.threatfabric.entities.Device;
import com.threatfabric.exceptions.DetectionNotFoundException;
import com.threatfabric.exceptions.DeviceNotFoundException;
import com.threatfabric.repositories.DetectionRepository;
import com.threatfabric.repositories.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DetectionServiceImpl implements DetectionService {

    @Autowired
    private DetectionRepository detectionRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Override
    public List<Detection> findAllDetections() {
        List<Detection> detections = detectionRepository.findAll();
        List<Detection> sortedDetection = detections.stream().sorted(detectionComparator()).collect(Collectors.toList());
        return sortedDetection;
    }

    @Override
    public List<Detection> fetchAllDetectionsByDeviceId(Long deviceId) {
        Optional<Device> deviceOpt = deviceRepository.findById(deviceId);
        List<Detection> detections = new ArrayList<>();
        if (deviceOpt.isPresent()) {
            Device device = deviceOpt.get();
            detections = device.getDetections().stream().sorted(detectionComparator()).collect(Collectors.toList());
        }
        return detections;
    }

    @Override
    public Optional<Detection> findDetectionById(Long deviceId, Long detectionId) {
        Optional<Device> deviceOpt = deviceRepository.findById(deviceId);
        if (deviceOpt.isPresent()) {
            Device device = deviceOpt.get();
            return device.getDetections().stream().filter(detection -> detectionId.equals(detection.getDetectionId())).findFirst();
        } else {
            throw new DeviceNotFoundException("Device with Id " + deviceId + " not found!");
        }
    }

    @Override
    public List<Detection> findDetectionBetweenDates(Long from, Long to) {
        List<Detection> detections = this.findAllDetections();
        List<Detection> filteredListByDate = filterByFromToDate(from, to, detections);
        return filteredListByDate;
    }

    @Override
    public List<Detection> findDetectionsByAppType(String byapptype) {
        List<Detection> detections = detectionRepository.findByTypeOfApp(byapptype);
        return detections;
    }

    @Override
    public void saveDetections(DetectionRequest detectionReq) {
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
        } else {
            throw new DeviceNotFoundException("Device with Id " + detectionReq.getDeviceId() + " not found!");
        }
    }

    @Override
    public void resolvedDetection(Long deviceId, Long detectionId) {
        Optional<Device> deviceOpt = deviceRepository.findById(deviceId);
        if (deviceOpt.isPresent()) {
            Device device = deviceOpt.get();
            deviceRepository.save(device);
            Optional<Detection> associatedDetectionOpt = device.getDetections().stream().filter(detection -> detectionId.equals(detection.getDetectionId())).findFirst();
            if (associatedDetectionOpt.isPresent()) {
                Detection associatedDetection = associatedDetectionOpt.get();
                associatedDetection.setDetectionType(DetectionType.RESOLVED_DETECTION);
                detectionRepository.save(associatedDetection);
            } else {
                throw new DetectionNotFoundException("Detection with Id " + detectionId + " not found with associated device id " + deviceId);
            }

        } else {
            throw new DeviceNotFoundException("Device with Id " + deviceId + " not found!");
        }
    }

    @Override
    public void clearDetectionById(Long deviceId, Long detectionId) {
        Optional<Device> deviceOpt = deviceRepository.findById(deviceId);
        if (deviceOpt.isPresent()) {
            Device device = deviceOpt.get();
            Optional<Detection> associatedDetectionOpt = device.getDetections().stream().filter(detection -> detectionId.equals(detection.getDetectionId())).findFirst();
            if (associatedDetectionOpt.isPresent()) {
                Detection associatedDetection = associatedDetectionOpt.get();
                device.removeDetection(associatedDetection);
                deviceRepository.save(device);
                associatedDetection.setDetectionType(DetectionType.NO_DETECTION);
                detectionRepository.delete(associatedDetection);
            } else {
                throw new DetectionNotFoundException("Detection with Id " + detectionId + " not found with associated with device id " + deviceId);
            }

        } else {
            throw new DeviceNotFoundException("Device with Id " + deviceId + " not found!");
        }
    }

    @Override
    public void clearAllDetectionOfDevice(Long deviceId) {
        Optional<Device> deviceOpt = deviceRepository.findById(deviceId);
        if (deviceOpt.isPresent()) {
            Device device = deviceOpt.get();
            List<Detection> detections = device.getDetections();
            device.removeAllDetections(detections);
            detectionRepository.deleteAll(detections);
        } else {
            throw new DeviceNotFoundException("Device with Id " + deviceId + " not found!");
        }
    }

    /**
     * @return sorting detection by time
     */
    private Comparator<Detection> detectionComparator() {
        return Comparator.comparing(Detection::getTime, (o1, o2) -> {
            return o2.compareTo(o1);
        });
    }

    /**
     * @param from
     * @param to
     * @param detections
     * @return collection of filtered detections by "from" -> "to" time
     */
    private List<Detection> filterByFromToDate(Long from, Long to, List<Detection> detections) {
        return detections.stream().filter(detection -> detection.getTime() >= from && detection.getTime() < to).collect(Collectors.toList());
    }
}
