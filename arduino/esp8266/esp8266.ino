#include <ESP8266WiFi.h>
#include <PubSubClient.h>
#include <Wire.h>

const char* ssid = "vudq";
const char* password = "29121995";
const char* mqtt_server = "192.168.100.11";

WiFiClient espClient;
PubSubClient client(espClient);
long lastMsg = 0;
char msg[50];
int value = 0;
int OUTPUT_LED = 5;

void setup_wifi() {

  delay(10);
  // We start by connecting to a WiFi network
  Serial.println();
  Serial.print("Connecting to ");
  Serial.println(ssid);

  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  randomSeed(micros());

  Serial.println("");
  Serial.println("WiFi connected");
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());
}
// config for mqtt
char roomAss[8] = {};
char port[8] = {}; 

void callback(char* topic, byte* payload, unsigned int length) {
  Serial.print("Message arrived [");
  Serial.print(strlen(topic));
  Serial.println("] ");
  int numOfIndex = 0;
  int indexRoomss =0;
  int indexPort = 0;
  for (int i = 0; i < strlen(topic); i++) {
    if(numOfIndex == 0){
      if(topic[i] == '/')
      {
        numOfIndex++;
      }
    }
    else if(numOfIndex == 1){
      if(topic[i] == '/')
      {
        numOfIndex++;
      }
      else{
        roomAss[indexRoomss] = topic[i];
        indexRoomss++;
      }
    }
    else if(numOfIndex == 2){
        port[indexPort] = topic[i];
        indexPort++;
    }
  }
      Serial.println(roomAss);
  
  for (int i = 0; i < length; i++) {
          port[indexPort] = (char)payload[i];
            indexPort++;
  }
  
  Serial.println(port);
  int roomNum = atoi(roomAss);
  Serial.println(roomNum);
  Wire.beginTransmission(roomNum); // Bắt đầu truyền dữ liệu về địa chỉ số 6
  Wire.write(port); // Truyền ký tự H
  Wire.endTransmission(); // kết thúc truyền dữ liệu
}

void reconnect() {
  // Loop until we're reconnected
  while (!client.connected()) {
    Serial.print("Attempting MQTT connection...");
    // Create a random client ID
    String clientId = "ESP8266Client-";
    clientId += String(random(0xffff), HEX);
    // Attempt to connect
    if (client.connect(clientId.c_str(),"vudq","1")) {
      Serial.println("connected");
      // Once connected, publish an announcement...
      client.publish("outTopic", "hello world");
      // ... and resubscribe
    Serial.print("subscribe ne`.");
      client.subscribe("DV1/#");
    } else {
      Serial.print("failed, rc=");
      Serial.print(client.state());
      Serial.println(" try again in 5 seconds");
      // Wait 5 seconds before retrying
      delay(5000);
    }
  }
}


void setup() {
  // put your setup code here, to run once:
  pinMode(OUTPUT_LED, OUTPUT);     // Initialize the LED_BUILTIN pin as an output
  Serial.begin(115200);
        Serial.print("init");
  Wire.begin(); // Khởi tạo thư viện i2c

  setup_wifi();
  client.setServer(mqtt_server, 1884);
  client.setCallback(callback);
}

void loop() {
  // put your main code here, to run repeatedly:
  if (!client.connected()) {
    reconnect();
  }
  client.loop();

  long now = millis();
  if (now - lastMsg > 2000) {
    lastMsg = now;
    ++value;
    snprintf (msg, 50, "hello world #%ld", value);
//    Serial.print("Publish message: ");
//    Serial.println(msg);
//    client.publish("outTopic", msg);
  }
}
