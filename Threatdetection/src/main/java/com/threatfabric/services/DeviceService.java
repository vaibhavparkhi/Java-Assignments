package com.threatfabric.services;

import com.threatfabric.entities.Device;

import java.util.List;
import java.util.Optional;

public interface DeviceService {
    public List<Device> findAllDevices();
    public Optional<Device> findDeviceById(Long deviceId);
    public List<Device> findAllDevicesByDeviceType(String deviceType);
    public void addNewDevice(Device device);
    public void deleteDeviceById(Long deviceId);
}
