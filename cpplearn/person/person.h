#include <string>
using std::string;

class Person{
    public:
        // Constructor
        Person(string name, int age);

        // Mutator
        void setAge(int age);

        // Accessors
        string getName();
        int getAge();
        // Print out fill information
        void print();

    private:
        string _name;
        int _age;
};
