#include <Wire.h>
char port[8] = "";
char value[8] = "";
char mss[8] = "";

void setup()
{
   Serial.begin(115200);
  Wire.begin(20); // Khởi tạo thư viện i2c địa chỉ 6
  Wire.onReceive(receiveEvent); // khởi tạo chế độ nhận tín hiệu từ boad chủ
  pinMode(13,OUTPUT);
  digitalWrite(13,LOW);
}
 
void loop()
{
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
