# Java-Assignments --> Threat Detection

### Overview
The REST API would be used on the server-side to get notifications about malware detections from the clients for scanned devices. 

### Technologies Used: 
Java 8, Spring Boot, Spring Data, Hibernate, H2 Database, Lombok

## Solution Description:
**At data layer** used entity relationship using Spring Data JPA which has to entity classes Device and Detection. The device has the @OnetoMany relationship with Detection. And Detection entity has a relationship @ManyToOne with Device. To remove detection for the particular device, I used the soft deletion strategy of Hibernate which could be used for further analytics of data for detection reports. Using Spring JPA layer to connects with database for CRUD operation. 

**At business layer** used detection & device services and exposes APIs consumed by controller layer. 

**At controller layer** exposes serval REST API using spring boot like save detection, resolve detection, delete detection, etc. as per the following mentioned API. 

## API Details:

### Detection REST APIs

1. **/v1/detections** GET API return list of all detections irrespective of device with most recent detection at top. 
2. **/v1/detections/{deviceId}** GET API return list of all detections respective of device id with most recent detection at top.
3. **/v1/detections/{deviceId}/{detectionId}** GET API returns detection by passing detectionId and deviceId.
4. **/v1/detections/fromto/{from}/{to}** GET API returns the detection from date to date or api error.
5. **/v1/detections/byapptype/{byapptype}** GET API returns detections by app type
6. **/v1/detections** POST API save the detection for particular device id with DetectionRequest
7. **/v1/detections/{deviceId}/{detectionId}** PUT API update the resolved detection on succesful transaction
8. **/v1/detections/{deviceId}/{detectionId}** DELETE API delete the already resolved detection on succesful transaction(soft deletion at DB level)

### Device REST APIs
1. **v1/devices** GET API return list of all devices. 
2. **v1/devices/{deviceId}** GET API return particular device by passing deviceId.
3. **v1/devices** POST API to save the device.
4. **v1/devices/{deviceId}** DELETE API to delete particular device. 

### Unit Test
Added few unit test cases for **DetectionRepository** and **DeviceRepository**
