/**
Example of code that is theoretically correct but would exhibit "bouncing".
*/

#define BUTTON 4
#define LED 8

// State of the input pin
int val = 0;
// Previous value of val
int oldVal = 0;
// 0 == off, 1 == on
int state = 0;

void setup(){
    pinMode(LED, OUTPUT);
    pinMode(BUTTON, INPUT);
}

void loop(){
    val = digitalRead(BUTTON);

    // check for transition
    if((val == HIGH) && (oldVal == LOW)){
        state = 1 - state;
    }

    oldVal = val;

    if(state == 1){
        digitalWrite(LED, HIGH);
    } else{
        digitalWrite(LED, LOW);
    }
}
