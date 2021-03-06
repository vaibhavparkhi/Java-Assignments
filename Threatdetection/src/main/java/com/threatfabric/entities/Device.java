package com.threatfabric.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "DEVICE_INFO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Device implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long deviceUid;

	@NotBlank(message = "Device type is mandatory")
	private String deviceType;

	@NotBlank(message = "Device model is mandatory")
	private String deviceModel;

	@NotBlank(message = "Device model is mandatory")
	private String osVersion;

	@OneToMany(mappedBy = "device")
	@ToString.Exclude
	private List<Detection> detections = new ArrayList();

	public Device(String deviceType, String deviceModel, String osVersion){
		this.deviceType = deviceType;
		this.deviceModel = deviceModel;
		this.osVersion = osVersion;
	}

	public void addDetection(Detection detection) {
		this.detections.add(detection);
	}

	public void removeDetection(Detection detection) {
		this.detections.remove(detection);
	}

	public void removeAllDetections(List<Detection> detections) {
		this.detections.removeAll(detections);
	}

}
