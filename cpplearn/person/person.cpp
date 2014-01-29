#include "person.h"
#include <iostream>
using std::cout;

// WHOA what is this notation??!?!?!?!??!
Person::Person(string name, int age): _name(name), _age(age)
{}

// Java-ish constructor syntax
// NOTE: This does not seem to work :|
//Person::Person(String name, int age){
//    _name = name;
//    _age = age;
//}

void Person::setAge(int age){
    _age = age;
}

string Person::getName(){
    return _name;
}

int Person::getAge(){
    return _age;
}

void Person::print(){
    cout << "Name: " << _name << "Age:" << _age;
}
