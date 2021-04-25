package com.threatfabric.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.threatfabric.entities.Detection;
import com.threatfabric.entities.Device;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class DeviceRepositoryTest {

	private final Logger logger = LoggerFactory.getLogger(DeviceRepositoryTest.class);

	@Autowired
	private DeviceRepository deviceRespository;

	@Test
	@DirtiesContext
	public void findAllDevicesTest() {
		List<Device> allDevices = deviceRespository.findAll();
		assertThat(allDevices).isNotEmpty();
	}

	@Test
	@DirtiesContext
	public void findAllById_Test() {
		Optional<Device> device = deviceRespository.findById(10001L);
		assertThat(device).isNotNull();
	}

	@Test
	@DirtiesContext
	@Transactional
	public void findAllDetectionsByDeviceId_Test() {
		Optional<Device> device = deviceRespository.findById(10001L);
		List<Detection> detections = null;
		if (device.isPresent()) {
			detections = device.get().getDetections();
		}
		System.out.println(detections);
		assertThat(detections).isNotEmpty();
	}

	@Test
	@DirtiesContext
	public void findNotExistDeviceById_Test() {
		Optional<Device> device = deviceRespository.findById(20001L);
		System.out.println(device);
		assertThat(device).isEmpty();
	}

}
