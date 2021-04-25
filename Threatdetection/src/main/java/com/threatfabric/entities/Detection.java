package com.threatfabric.entities;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE detection " +
		"SET deleted = true " +
		"WHERE detection_id = ?")
@Where(clause = "deleted = false")
public class Detection implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long detectionId;

	@NotNull(message = "time is mandatory")

	@Column(name = "time_stamp") // persist in the format of epoch time
	private Long time;

	@NotNull(message = "Name of Application is mandatory")
	private String nameOfApp;

	@NotNull(message = "Type of Application is mandatory")
	private String typeOfApp;

	@NotNull(message = "Detection type of Application is mandatory")
	@Enumerated(EnumType.STRING)
	private DetectionType detectionType;

	@ManyToOne
	@JoinColumn(name = "DEVICE_DETECTION_ID")
	@JsonIgnore
	private Device device;

	@JsonIgnore
	private boolean deleted;

	public Detection(Long time, String nameOfApp, String typeOfApp, DetectionType detectionType){
		this.time = time;
		this.nameOfApp = nameOfApp;
		this. typeOfApp = typeOfApp;
		this.detectionType = detectionType;
	}

}
