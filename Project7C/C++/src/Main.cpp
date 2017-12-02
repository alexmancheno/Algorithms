#include <iostream>
#include <fstream>
using namespace std;

int hashFunc(int x) { return x; }

int main(int argc, char** argv)
{
    // Step 0: initialize variables, find max number from input, and 
    // dynamically allocate BucketAry
    ifstream input;
    ofstream output;
    int max, data, index;
    int* BucketAry;

    input.open(argv[1]);  
    output.open(argv[2]);
    input >> max; 
    while (!input.eof())
    {
        input >> data;
        if (data > max) max = data; 
    }
    input.close();

    BucketAry = new int[max + 1]{0};

    // Step 1-4: tally up each occurence of each number from input in BucketAry
    input.open(argv[1]);
    while(!input.eof())
    {
        input >> data;
        index = hashFunc(data);
        BucketAry[index]++;
    }

    // Step 5:
    index = 0;

    // Step 6-8: print out sorted numbers
    while (index <= max)
    {
        while (BucketAry[index] > 0)
        {
            output << index << " ";
            BucketAry[index]--;
        }
        index++;
    } 
    input.close();
    output.close();
}