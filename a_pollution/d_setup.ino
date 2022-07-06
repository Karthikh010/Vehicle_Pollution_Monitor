String processor(const String& var) {
  if(var== "BUTTONPLACEHOLDER"){
    String buttons=String(read_mq2());
    buttons += ",";
    buttons += String(read_mq7());
    buttons += ",";
    buttons += String(read_mq135());
    return buttons;
  }
  return String();
}
const char index_html[] PROGMEM = R"rawliteral(
%BUTTONPLACEHOLDER%
)rawliteral";

//--------------- static IP ADDRESS -------------
IPAddress local_IP(192, 168, 43, 4);
// Set your Gateway IP address
IPAddress gateway(192, 168, 1, 1);

IPAddress subnet(255, 255, 0, 0);
IPAddress primaryDNS(8, 8, 8, 8);   //optional
IPAddress secondaryDNS(8, 8, 4, 4); //optional

void setup() {
  Serial.begin(115200);
  setup_mq135();
  if (!WiFi.config(local_IP, gateway, subnet, primaryDNS, secondaryDNS)) {
    Serial.println("STA Failed to configure");
  }
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
    delay(1000);
    Serial.println("Connecting to WiFi..");
  }

  Serial.println(WiFi.localIP());
  server.on("/", HTTP_GET, [](AsyncWebServerRequest *request){
    Serial.println("Got request");
    request->send_P(200, "text/html", index_html, processor);
  });

     server.on("/read_mq7", HTTP_GET, [](AsyncWebServerRequest *request){
    Serial.println("Got buzzer request ON  ");
    read_mq2();
    //request->send_P(200, "text/html",  index_html, processor);
  });


 

  server.begin();

}
