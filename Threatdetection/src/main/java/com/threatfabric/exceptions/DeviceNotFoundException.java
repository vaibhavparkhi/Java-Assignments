package com.threatfabric.exceptions;

public class DeviceNotFoundException extends RuntimeException {

    public DeviceNotFoundException(String message){
        super(message);
    }
}
