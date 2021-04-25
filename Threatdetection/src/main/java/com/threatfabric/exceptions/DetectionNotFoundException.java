package com.threatfabric.exceptions;

public class DetectionNotFoundException extends RuntimeException {

    public DetectionNotFoundException(String message){
        super(message);
    }
}
