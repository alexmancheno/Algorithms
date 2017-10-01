#include <iostream>
using namespace std;
struct testing
{
  int abc;

  testing() { abc = 10;}
  
  void testFunc(int abc)
  {
    cout << this->abc << endl;
  }
};

int main() {
    testing myTest;
    myTest.testFunc(123);
    return 0;
  }