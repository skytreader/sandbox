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
    
    if(val == HIGH){
        if(toggleFlag == LOW){
            toggleFlag = HIGH;
        } else{
            toggleFlag = LOW;
        }
    }

    digitalWrite(LED, toggleFlag);
}
