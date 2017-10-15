#include <iostream> 
#include <fstream>
using namespace std;

struct HeapSort
{
    int rootIndex, fatherIndex, leftKidIndex, rightKidIndex, minKidIndex;
    int* heapAry;
    int heapSize; // To keep track of dynamic array size

    HeapSort(int n)
    {
        rootIndex = 1; fatherIndex = 0; leftKidIndex = 0; rightKidIndex = 0; minKidIndex = 0;
        heapAry = new int[n];
        heapAry[0] = 0;
        heapSize = n;
    }

    void buildHeap(ifstream& input, ofstream& output1) 
    {
        rootIndex = 1;
        int data, kidIndex;
        while (!input.eof())
        {
            input >> data;
            insertOneDataItem(data);
            kidIndex = heapAry[0];
            bubbleUp(kidIndex);
            printHeap();
            output1 << toString() << "\n";
        }
    }

    void deleteHeap(ofstream& output1, ofstream& output2)
    {
        int data;
        while (!isHeapEmpty())
        {
            data = getRoot();
            // Print root data 
            output2 << data << endl;
            cout << data << endl;
            
            // Swap root with last, delete last, and bubble down new root
            deleteRoot();
            fatherIndex = rootIndex;
            bubbleDown(fatherIndex);

            // Print heap
            output1 << toString() << endl;
            printHeap();
        }
    }

    void insertOneDataItem(int data)
    {
        if (!isHeapFull())
            heapAry[++heapAry[0]] = data;
        else
            cout << "Heap is full!\n";
    }

    int getRoot() { return heapAry[1]; }

    void deleteRoot()
    {
        swap(rootIndex, heapAry[0]);
        heapAry[0]--;
    }

    void bubbleUp(int kidIndex)
    {
        if (!isRoot(kidIndex))
        {
            fatherIndex = kidIndex / 2;
            if (heapAry[kidIndex] < heapAry[fatherIndex])
            {
                swap(kidIndex, fatherIndex);
                bubbleUp(fatherIndex);
            }
        }
    }

    void bubbleDown(int fatherIndex)
    {
        if (!isLeaf(fatherIndex))
        {
            leftKidIndex = fatherIndex * 2;
            rightKidIndex = fatherIndex * 2 + 1;
            minKidIndex = findMinKidIndex(leftKidIndex, rightKidIndex);

            if (heapAry[minKidIndex] < heapAry[fatherIndex])
            {
                swap(minKidIndex, fatherIndex);
                bubbleDown(minKidIndex);
            }
        }
    }

    void swap(int a, int b)
    {
        int temp = heapAry[a];
        heapAry[a] = heapAry[b];
        heapAry[b] = temp;
    }

    bool isLeaf(int index) { return index * 2 > heapAry[0]; }
    
    bool isRoot(int index) { return index == 1; }

    int findMinKidIndex(int leftKidIndex, int rightKidIndex)
    {
        if (rightKidIndex > heapAry[0]) return leftKidIndex;
        if (heapAry[rightKidIndex] > heapAry[leftKidIndex]) 
            return leftKidIndex;
        else
            return rightKidIndex; 
    }
    
    bool isHeapEmpty() { return heapAry[0] == 0; }

    bool isHeapFull() { return heapAry[0] == heapSize - 1; }

    void printHeap() { cout << toString() << endl; }

    string toString() 
    {
        string s = "";
        for (int i = 0; i <= heapAry[0]; i++) s += to_string(heapAry[i]) + " ";
        return s;
    }
};

int main(int argc, char** argv)
{
    ifstream input;
    ofstream output1, output2;

    input.open(argv[1]);    // Input file
    output1.open(argv[2]);  // Output for debugging
    output2.open(argv[3]);  // Output for sorted numbers

    int count = 0;
    int num;
    // Count how many numbers are in the file
    while (!input.eof())
    {
        input >> num;
        count++;
    }
    input.close();

    // Initialize HeapSort object containing dynamic 1D array with size of input + 1
    HeapSort heap(count + 1);

    // Build the heap
    input.open(argv[1]);
    output1 << "buildHeap:\n";
    heap.buildHeap(input, output1);

    // Output sorted numbers by destroying heap
    output1 << "deleteHeap (debugging output):\n";
    cout << "deleteHeap:\n";
    output2 << "deleteHeap (sorted output):\n";
    heap.deleteHeap(output1, output2);

    // Close all files
    input.close();
    output1.close();
    output2.close();
    return 0;
}