package com.threatfabric.controllers;

import com.threatfabric.entities.Device;
import com.threatfabric.exceptions.DeviceNotFoundException;
import com.threatfabric.services.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@RestController
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @GetMapping("v1/devices")
    public ResponseEntity getAllDevices(){
        List<Device> devices = deviceService.findAllDevices();
        if(devices.size() > 0 ){
            return new ResponseEntity(devices, HttpStatus.OK);
        }else{
            throw new DeviceNotFoundException("Device(s) not found!");
        }
    }

    @GetMapping("v1/devices/{deviceId}")
    public ResponseEntity findDeviceById(@PathVariable @NotBlank Long deviceId){
        Optional<Device> deviceOpt = deviceService.findDeviceById(deviceId);
        if(deviceOpt.isPresent()){
            return new ResponseEntity(deviceOpt.get(), HttpStatus.OK);
        }else{
            throw new DeviceNotFoundException("Device for Id " + deviceId + " not found!");
        }
    }

    @PostMapping("v1/devices")
    public ResponseEntity addNewDevice(@RequestBody @NotNull Device device){
        deviceService.addNewDevice(device);
        return new ResponseEntity("Added device successfully", HttpStatus.OK);
    }

    @DeleteMapping("v1/devices/{deviceId}")
    public ResponseEntity deleteDeviceById(@PathVariable @NotBlank Long deviceId){
        deviceService.deleteDeviceById(deviceId);
        return new ResponseEntity("Deleted device successfully", HttpStatus.OK);
    }
}
