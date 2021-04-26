package com.threatfabric.repositories;

import com.threatfabric.entities.Detection;
import com.threatfabric.entities.DetectionType;
import com.threatfabric.entities.Device;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class DetectionRepositoryTest {

    @Autowired
    private DetectionRepository detectionRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Test
    @DirtiesContext
    public void findDetectionById_Test(){
        Optional<Detection> optionalDetection = detectionRepository.findById(20001L);
        assertThat(optionalDetection).isNotNull();
    }

    @Test
    @DirtiesContext
    public void findAllDetections(){
        List<Detection> detections = detectionRepository.findAll();
        assertThat(detections).isNotEmpty();
    }

    @Test
    @DirtiesContext
    @Transactional
    public void saveDetection_test(){
        Optional<Device> device = deviceRepository.findById(10001L);
        Detection detection = null;
        if(device.isPresent()){
            detection= new Detection(Instant.now().toEpochMilli(), "Google Chrome", "Hybrid", DetectionType.NEW_DETECTION);
            detection.setDevice(device.get());
            detection.setDeleted(false);
            device.get().addDetection(detection);
            detectionRepository.save(detection);
        }
        List<Detection> alldetections = detectionRepository.findAll();

        assertThat(alldetections).size().isEqualTo(4);

    }

    @Test
    @DirtiesContext
    public void deleteDetectionById_Test(){
        detectionRepository.deleteById(20001L);
        List<Detection> alldetections = detectionRepository.findAll();
        assertThat(alldetections).size().isEqualTo(2);
    }

    @Test
    @DirtiesContext
    @Transactional
    public void deleteAllDetectionOfDevice_Test() {
        Optional<Device> device = deviceRepository.findById(10001L);
        List<Detection> detections = device.get().getDetections();
        System.out.println(detections.size());
        device.get().removeAllDetections(detections);
        detectionRepository.deleteAll(detections);
        assertThat(device.get().getDetections()).size().isEqualTo(0);
    }

    @Test
    @DirtiesContext
    @Transactional
    public void resolvedDetection_test(){
        Optional<Device> device = deviceRepository.findById(10001L);
        Optional<Detection> detection = detectionRepository.findById(20001L);
        if(device.isPresent() && detection.isPresent()){
            detection.get().setDetectionType(DetectionType.RESOLVED_DETECTION);
            detectionRepository.save(detection.get());
        }
        assertThat(detection.get().getDetectionType()).isEqualTo(DetectionType.RESOLVED_DETECTION);
    }
}
