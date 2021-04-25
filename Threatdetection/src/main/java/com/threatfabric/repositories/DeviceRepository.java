package com.threatfabric.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threatfabric.entities.Device;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface DeviceRepository extends JpaRepository<Device, Long> {

}
