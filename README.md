# MyDoAn
Đồ án IOT
- Devive:
	+ Main processor : Arduino
	+ ESP8266, Sensor
- Technology used:
	+ Back-end HTTP : Java (Spring, Hibernate)
	+ Broker MQTT, broker websocket : vert-x
	+ Web client (Web Front-end) : Angular JS
	+ Android native : Java
	+ Service checkTime : Java Service
- subcribe: 
	+ topic : "mã đơn vị/#": mã đơn vị ăn theo tài khoản. madv/#. subcribe tất cả topic madv/... (vd: madv/1,madv/2,madv/3,madv/abcabcabc ... )
- publish : cũng publish với cấu trúc trên. madv/port
	+ madv :  ăn theo tài khoản
	+ port : được tạo theo từng thiết bị, khi click vào button thiết bị nào đó, sẽ tự gen ra topic như trên
	
