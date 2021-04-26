package com.threatfabric.services;

import com.threatfabric.entities.Device;
import com.threatfabric.repositories.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    @Override
    public List<Device> findAllDevices() {
        return deviceRepository.findAll();
    }

    @Override
    public Optional<Device> findDeviceById(Long deviceId) {
        return deviceRepository.findById(deviceId);
    }

    @Override
    public List<Device> findAllDevicesByDeviceType(String deviceType) {
       return deviceRepository.findByDeviceType(deviceType);
    }

    @Override
    public void addNewDevice(Device device) {
        deviceRepository.save(device);
    }

    @Override
    public void deleteDeviceById(Long deviceId) {
        deviceRepository.deleteById(deviceId);
    }
}
