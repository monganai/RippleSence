

                 

***** Migrated From https://gitlab.scss.tcd.ie/monganai/Ripple/tree/mergeBranch *****

                   ***Contributers and commit info availible on gitlab***
                  
 Prototype app and arduino code/schematic for RippleSence 
 
 - https://www.nibsweb.org/2017/05/18/dit-wins-2017-business-plan-competition/
 - https://boltontrust.ie/ripple-sense-water-level-monitor-wins-dit-student-competition-2017/


To fully test you will need an arduino with: 

 -hco5 blutooth module 
 -ultrasonic sensor ( hc-sr04)
 -digital temperature and humidity sensor (dht11)
 -1kohm resistor
 -2kohm resistor 
 -wires
 -breadboard(optional)

Arduino Connections:

 arduino 5v to : 
  hc-sr04 vcc
  hc-05 vcc
  DHT11  vcc

 arduino ground to:
  hc-sr04 ground
  hc-05 ground
  DHT  ground

 arduino tx --  1kohm resister --- point X ---- 2kohm resister ------ground
  point X -> hco5 Rx  (  roughly 3.3v required by hc-05)
  arduino rx to hco5 tx

 arduino digital pins:
  2-> hc-sr04 echo
  3-> hc-sr04 trigger
  4-> dht11 data



pair android phone with hc-05  (required only once )  password 1234

launch app

app is very intuaitive and written insturctions are trivial, on the loginscreen press the help icon to recieve email support if needed.

when adding a ripple ensure the correct dimensions are entered :
the distance from the ultrasonic sensor to the bottom of the container and the depth of the water in centimetres .
water level at time of adding a ripple is irrelavant;
 
 
