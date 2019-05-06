#include <DallasTemperature.h>

#include <OneWire.h>

#include <Timer.h>
#include <WorkScheduler.h>
//#include <onewire.h>
//#include <dallastemperature.h>
#include <Wire.h>
#include <math.h>

char port[8] = "";
char value[8] = "";
char mss[8] = "";
int sensorPin = A0;// chân analog kết nối tới cảm biến LM35
// Chân nối với Arduino
#define ONE_WIRE_BUS 2
//Thiết đặt thư viện onewire
OneWire oneWire(ONE_WIRE_BUS);
//Mình dùng thư viện DallasTemperature để đọc cho nhanh
DallasTemperature sensors(&oneWire);

/*
* Chương trình ví dụ 1:
* 
*/
float curentTemp = 0;
//khởi tạo các job
WorkScheduler *printToSerialScheduler;
WorkScheduler *printToSerial100msScheduler;


//chúng ta không dùng pin gì hết vì mình đã viết thêm một cập nhập nhỏ
void printToSerial() {
  static unsigned long counter = 0;
  counter++;
  Serial.print("kLaserCutter - ksp. Lan thu: ");
  Serial.println(counter);
}

void printToSerial100ms() {
    Wire.write(curentTemp);

//  static unsigned long counter = 0;
//  counter++;
//  Serial.print("ksp's handsome!!! Lan thu: ");
//  Serial.println(counter);
}

void setup()
{
   Serial.begin(115200);
  Wire.begin(20); // Khởi tạo thư viện i2c địa chỉ 6
  Wire.onReceive(receiveEvent); // khởi tạo chế độ nhận tín hiệu từ boad chủ
  pinMode(13,OUTPUT);
  digitalWrite(13,LOW);
    Timer::getInstance()->initialize();
      //Khởi tạo một công việc (job) - không đùng đến một pin cụ thể nào đó mà chỉ thực hiện các tác vụ như in serial monitor hoăc đọc các cảm biến có nhiều chân ^_^  
  //print ra nhanh hơn
  printToSerial100msScheduler = new WorkScheduler(3000UL, printToSerial100ms);
    sensors.begin();



}
 
void loop()
{
    //đầu hàm loop phải có để cập nhập thời điểm diễn ra việc kiểm tra lại các tiến trình
  Timer::getInstance()->update();

  //Không quan trọng thứ tự các job, các job này là các job thực hiện các công việc độc lập với nhau
//  printToSerialScheduler->update();
  printToSerial100msScheduler->update();


  //cuối hàm loop phải có để cập nhập lại THỜI ĐIỂM (thời điểm chứ ko phải thời gian nha, tuy tiếng Anh chúng đều là time) để cho lần xử lý sau
  Timer::getInstance()->resetTick();
  sensors.requestTemperatures();  
  Serial.print("Nhiet do");
  Serial.println(sensors.getTempCByIndex(0)); // vì 1 ic nên dùng 0
  float temp = sensors.getTempCByIndex(0);
  if(abs(curentTemp - temp) > 3.00){
    Wire.write(curentTemp);
  }
   curentTemp = temp;
  //chờ 1 s rồi đọc để bạn kiệp thấy sự thay đổi
  //delay(1000);

}
 
void receiveEvent(int howMany) // hàm sự kiện nhận tín hiệu từ boad chủ
{
    for (int i = 0; i < howMany; i++)
    {
//       Serial.println(i);
//       Serial.println(Wire.read());    
       if(i == 0 || i ==1){
        port[i] = Wire.read();
      }
      else if (i==2){
        value[0] = Wire.read();
      }
    }
           Serial.println(value[0]);

    int portInt = atoi(port);
    int valueInt = atoi(&value[0]);
    
    digitalWrite(portInt,valueInt);
       Serial.println(portInt);
       Serial.println(valueInt);

//  while(Wire.available()) // chờ cho đến khi có tín hiệu
//  {
//    char c = Wire.read(); // biến c để lưu dữ liệu nhận được
//    
//    if(c == 'H') // nếu boad chủ gửi về tín hiệu là H
//    {
//      digitalWrite(13,HIGH); // chân 13 ở mức High
//    }
//    else // nếu tín hiệu boad chủ gửi về là L
//    {
//      digitalWrite(13,LOW);// chân 13 ở mức Low
//    }
//  }
}
