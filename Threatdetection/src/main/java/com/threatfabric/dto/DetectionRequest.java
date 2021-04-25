package com.threatfabric.dto;

import com.threatfabric.entities.Detection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetectionRequest {

    private Long deviceId;
    private Detection detection;
}
