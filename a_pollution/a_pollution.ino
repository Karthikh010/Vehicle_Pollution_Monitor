#include <WiFi.h>
#include <AsyncTCP.h>
#include <ESPAsyncWebServer.h>

const char* ssid = "kh";
const char* password = "12345678";

AsyncWebServer server(80);


// MQ7
float RS_gas = 0;
float ratio = 0;
float sensorValue = 0;
float sensor_volt = 0;
float R0 = 7200.0;
int MQ7_PIN = 34;

// MQ2
int MQ2_PIN = 35;
float gassensorAnalog;

//MQ135
#include <MQUnifiedsensor.h>
#define placa "ESP-32"
#define Voltage_Resolution 3.3
#define pin 33
#define type "MQ-135"
#define ADC_Bit_Resolution 12
#define RatioMQ135CleanAir 3.6  
double CO2 = (0);  
MQUnifiedsensor MQ135(placa, Voltage_Resolution, ADC_Bit_Resolution, pin, type);
