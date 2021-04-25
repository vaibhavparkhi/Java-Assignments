package com.threatfabric.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class UtilsManager {

    public static LocalDateTime convertEpochToLocalDateTime(Long epoch){
        return Instant.ofEpochMilli(epoch).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
