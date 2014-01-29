#include <string>
using std::string;

/**
Verbatim copy of the Person class code in Java-C++ comparison module.
*/
class Person{
    public:
        // Constructor
        Person(string name, int age);

        // Mutator
        void setAge(int age);

        // Accessors
        string getName() const;
        int getAge() const;
        // Print out fill information
        void print() const;

    private:
        string _name;
        int _age;
};
