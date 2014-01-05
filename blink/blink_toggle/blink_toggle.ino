/**
My own take on making the switch a toggle (and not just a simple circuit breaker).
*/

#define LED 8
#define BUTTON 4

int val = 0;
int toggleFlag = LOW;

void setup(){
    pinMode(LED, OUTPUT);
    pinMode(BUTTON, INPUT);
}

void loop(){
    val = digitalRead(BUTTON);
    
    // If button was pressed on this turn...
    if(val == HIGH){
        if(toggleFlag == LOW){
            // LED is off, turn it on.
            toggleFlag = HIGH;
        } else{
            // LED is on turn it off.
            toggleFlag = LOW;
        }
        // De-bouncing code, add a 10ms delay.
        delay(50);
    }
    
    // And now write it to LED.
    digitalWrite(LED, toggleFlag);
}
