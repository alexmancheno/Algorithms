#include "LinkedList.cpp"
#include <fstream>

int main(int argc, char** argv)
{
    LinkedList* ll = new LinkedList();
    ifstream input;
    ofstream output;

    input.open(argv[1]);
    output.open(argv[2]);
    ll->printList();
    output << ll->toString() << endl;
    string word;

    while (input.good()) 
    {
        input >> word;
        ll->listInsert(word);
        output << ll->toString() << endl;
        ll->printList();
    }

    output.close();
    input.close();
    return 0;
}