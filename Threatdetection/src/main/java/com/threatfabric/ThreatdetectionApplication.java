package com.threatfabric;

import com.threatfabric.entities.Detection;
import com.threatfabric.entities.DetectionType;
import com.threatfabric.entities.Device;
import com.threatfabric.repositories.DetectionRepository;
import com.threatfabric.repositories.DeviceRepository;
import com.threatfabric.utils.UtilsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Instant;

@SpringBootApplication
public class ThreatdetectionApplication implements CommandLineRunner {

	@Autowired
	private DetectionRepository detectionRepository;

	@Autowired
	private DeviceRepository deviceRepository;

	public static void main(String[] args) {
		SpringApplication.run(ThreatdetectionApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		long epoch = Instant.now().toEpochMilli();
		Device device = new Device("IOS","IOS-G98880F","3.0");
		deviceRepository.save(device);
		Detection detection1 = new Detection(epoch, "PayTM", "Hybrid", DetectionType.NEW_DETECTION);
		detection1.setDevice(device);
		detection1.setDeleted(false);
		device.addDetection(detection1);
		detectionRepository.save(detection1);
		detectionRepository.deleteById(20004L);
	}
}
