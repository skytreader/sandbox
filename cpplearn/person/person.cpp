#include "person.h"
#include <iostream>
using std::cout;

// WHOA what is this notation??!?!?!?!??!
Person::Person(string name, int age): _name(name), _age(age)
{}

void Person::setAge(int age){
    _age = age;
}

string Person::getName() const{
    return _name;
}

int Person::getAge() const{
    return _age;
}

void Person::print() const{
    cout << "Name: " << _name << "Age:" << _age;
}
