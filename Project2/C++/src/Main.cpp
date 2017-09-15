#include <iostream>
#include <fstream>
#include <string>
using namespace std;

static void printAry(int charCounts[], ofstream& output)
{
    string s; 
    for (int i = 0; i < 256; i++)
    {
        if (charCounts[i] != 0)
        {
            s = "Char" + to_string(i) + " \'"+ (char)i + "\' # " + to_string(charCounts[i]) + "\n"; 
            output << s;
            cout << s;
        }
    }
}

int main(int argc, char** argv)
{
    ifstream input;
    ofstream output;

    input.open(argv[1]);
    output.open(argv[2]);
    
    int charCounts[256] = {0};
    int index;
    char charIn;

    while (input.good())
    {
        input >> charIn;
        index = (int)charIn;
        if (index < 256 && index != 8 && index != 9 && index != 10 && index != 13 && index != 11 && index != 32 && index != 160)
        {
            charCounts[index]++;
        }
    }

    printAry(charCounts, output);

    output.close();
    input.close();

    return 0;
}