void loop() {

   Serial.print("MQ7 PPM: ");
   Serial.println(read_mq7());
   Serial.print("MQ2 PPM: ");
   Serial.println(read_mq2());
   Serial.print("MQ135 PPM: ");
   Serial.println(read_mq135());
   delay(2000);
}
