--SELECT * FROM DEVICE_INFO;
--DEVICE_UID  	DEVICE_MODEL  	DEVICE_TYPE  	OS_VERSION  

insert into device_info(device_uid, device_model, device_type, os_version) values (10001, 'AD-G920F', 'ANDROID', '1.0');
insert into device_info(device_uid, device_model, device_type, os_version) values (10002, 'IO-G920F', 'IOS', '3.0');
insert into device_info(device_uid, device_model, device_type, os_version) values (10003, 'WEB-G920F', 'WEB', '2.0');

--SELECT * FROM DETECTION;
--DETECTION_ID  	DETECTION_TYPE  	NAME_OF_APP  	TIME  	TYPE_OF_APP  	DEVICE_DETECTION_ID  

insert into detection(detection_id, detection_type, name_of_app, time_stamp, type_of_app, device_detection_id, deleted) values (20001, 'NEW_DETECTION', 'App1', 1619333137651, 'Banking', 10001, false);
insert into detection(detection_id, detection_type, name_of_app, time_stamp, type_of_app, device_detection_id, deleted) values (20002, 'RESOLVED_DETECTION', 'App3', 1619333153984, 'Payment Gateway', 10002, false);
insert into detection(detection_id, detection_type, name_of_app, time_stamp, type_of_app, device_detection_id, deleted) values (20003, 'NO_DETECTION', 'App567', 1619333161533, 'Insurance', 10001, false);

