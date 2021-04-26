package com.threatfabric.repositories;

import com.threatfabric.entities.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface DeviceRepository extends JpaRepository<Device, Long> {

    List<Device> findByDeviceType(String deviceType);
}
