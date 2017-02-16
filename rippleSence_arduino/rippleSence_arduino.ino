#include <dht.h>





int tx=1;
int rx=0;
char inSerial[15];
dht DHT;
#define DHT11_PIN 4
#define echoPin 2 // Echo Pin
#define trigPin 3 // Trigger Pin
int maximumRange = 350; // Maximum range needed
int minimumRange = 0; // Minimum range needed
long duration, distance; // Duration used to calculate distance


void setup(){
  Serial.begin(9600);
  pinMode(tx, OUTPUT);        
  pinMode(rx, INPUT);
  pinMode(trigPin, OUTPUT);
  pinMode(echoPin, INPUT);
 

}

void loop(){
    int i=0;
    int m=0;
    delay(500);                                         
    if (Serial.available() > 0) {             
       while (Serial.available() > 0) {
         inSerial[i]=Serial.read(); 
         i++;      
       }
       inSerial[i]='\0';
      Check_Protocol(inSerial);
     }

     } 
     

  
void Check_Protocol(char inStr[]){   
  int i=0;
  int m=0;
  
  
 
   
   if(!strcmp(inStr,"ripple")){      //sensor on 
    
    
     digitalWrite(trigPin, LOW); 
     delayMicroseconds(2); 

     digitalWrite(trigPin, HIGH);
     delayMicroseconds(10); 
 
     digitalWrite(trigPin, LOW);
     duration = pulseIn(echoPin, HIGH);
     distance = duration/58.2;
     
     
     Serial.println(distance);
     
      for(m=0;m<11;m++){
      inStr[m]=0;}
       i=0;
       }
    


   
       else if(!strcmp(inStr,"dht")){  

         
  int chk = DHT.read11(DHT11_PIN);
  
  // display current sensor readings
  
  Serial.println(DHT.temperature, 1);
  

  delay(2000);
        
        
        
        
        
        }
       
    else{
    for(m=0;m<11;m++){
      inStr[m]=0;
    }
    i=0;

}
}
  
