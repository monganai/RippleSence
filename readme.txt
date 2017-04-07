
 mmmmm    "                  ""#            mmmm                             
 #   "# mmm    mmmm   mmmm     #     mmm   #"   "  mmm   m mm    mmm    mmm  
 #mmmm"   #    #" "#  #" "#    #    #"  #  "#mmm  #"  #  #"  #  #"  "  #"  # 
 #   "m   #    #   #  #   #    #    #""""      "# #""""  #   #  #      #"""" 
 #    " mm#mm  ##m#"  ##m#"    "mm  "#mm"  "mmm#" "#mm"  #   #  "#mm"  "#mm" 
               #      #                                                      
               "      "                                               




To fully test you will need an arduino with: 
hco5 blutooth module 
ultrasonic sensor ( hc-sr04)
digital temperature and humidity sensor (dht11)
1kohm resistor
2kohm resistor 
wires
breadboard(optional)


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
 the distance from the ultrasonic sensor to the bottom of the container and the depth of the water in centimetres . water level at time of adding a ripple is irrelavant;
 
 
 
 
 
 
 


