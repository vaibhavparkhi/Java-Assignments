package com.threatfabric.services;

import com.threatfabric.entities.Detection;
import com.threatfabric.repositories.DetectionRepository;
import com.threatfabric.repositories.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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
    public Optional<Detection> findDetectionById(Long detectionId) {
        return detectionRepository.findAllById(detectionId);
    }

    @Override
    public List<Detection> findDetectionBetweenDates(Long from, Long to) {
        List<Detection> detections = this.findAllDetection();
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
    public void updateDetection(Long deviceId, Long detectionId) {

    }

    @Override
    public void deleteDetectionById(Long detectionId) {

    }

    @Override
    public void deleteAllDetectionOfDevice(Long deviceId) {

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
