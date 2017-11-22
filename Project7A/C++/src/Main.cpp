#include <iostream>
#include <fstream>
using namespace std;

struct undirectedEdge
{
    int Ni, Nj, edgeCost;
    undirectedEdge* next;

    undirectedEdge(int i, int j, int e)
    {
        Ni = i; Nj = j; edgeCost = e;
        next = NULL; 
    }

    string toString()
    {
        return "(" + to_string(Ni) + ", " + to_string(Nj) + ", " + to_string(edgeCost) + ")";
    }

    ~undirectedEdge()
    {
        delete next;    
    }
};

struct KruskalMST
{
    int numNodes, numSets, totalMSTCost;
    int* inWhichSet; // to indicate which set each node belongs 
    undirectedEdge* MSTofG; // the head of a linkde list Stack, which points to dummy node <0, 0, 0> when created
    undirectedEdge* edgeListHead; // the head of a linked list, should point to a dummy node <0, 0, 0> when created

    KruskalMST(int n)
    {
        numNodes = n; numSets = n; totalMSTCost = 0;
        
        // Initialize 1-D array to size of n + 1
        inWhichSet = new int[n + 1];
        
        // Set each node to belong to itself
        for (int i = 0; i <= n; i++) inWhichSet[i] = i;

        // Set to a dummy node
        MSTofG = new undirectedEdge(0, 0, 0);

        // Set to a dummy node
        edgeListHead = new undirectedEdge(0, 0, 0);
    }

    // Insert edge into the list of edgeListHead in ascending order using insertion sort
    void insertEdge(undirectedEdge* newEdge)
    {
        undirectedEdge* i = edgeListHead;
        while (i->next != NULL && newEdge->edgeCost > i->next->edgeCost)
            i = i->next;
        newEdge->next = i->next;
        i->next = newEdge;
    }

    // Removes and returns the front edge node of edgeListHead
    undirectedEdge* removeEdge()
    {
        undirectedEdge* e = edgeListHead;
        edgeListHead = edgeListHead->next;
        return e;
    }

    // Push edge node to the top of MSTofG
    void pushEdge(undirectedEdge* edge)
    {
        edge->next = MSTofG;
        MSTofG = edge;
    }

    // Modify inWhichSet(node2) to node1 if node1 is smaller than node2, otherwise do the opposotie so that node1 and node2 are in the same set
    void merge2Sets(int node1, int node2)
    {
        if (node1 > node2) // to guarantee that node1 is less than node2
        {
            int temp = node1;
            node1 = node2;
            node2 = temp;
        }

        int newSet = inWhichSet[node2]; // save the old set of node2
        for (int i = 1; i <= numNodes; i++)  // change every node in node2's set to node1's set
            if (inWhichSet[i] == newSet) inWhichSet[i] = inWhichSet[node1];
    }

    // Print inWhichSet to output file (argv[3])
    void printSet(ofstream& output2)
    {
        output2 << "inWhichSet: ";
        for (int i = 1; i <= numNodes; i++)
            output2 << inWhichSet[i] << " ";
        output2 << "\n";
    }

    // Print the information in nodes <Ni, Nj, edgecost> in the given linked list to argv[3] 
    void printList(undirectedEdge* listHead, int n, ofstream& output2)
    {
        undirectedEdge* i = listHead;
        output2 << "listHead ->";
        while (i != NULL && n > 0)
        {
            output2 << i->toString() << "->";
            i = i->next;
            n--;
        }
        output2 << "NULL\n";
    }
};

int main(int argc, char** argv)
{
    ifstream input;
    ofstream output1, output2;
    int numberOfNodes, Ni, Nj, cost;
    undirectedEdge* nextEdge;

    input.open(argv[1]);    // Open input file
    output1.open(argv[2]);  // For the MST of G
    output2.open(argv[3]);  // For debugging purposes

    // Step 0:
    input >> numberOfNodes;
    KruskalMST kruskal(numberOfNodes); 
    kruskal.printSet(output2);

    while (!input.eof()) // Step 3: repeat steps 1-2 until eof
    {
        // Step 1:
        input >> Ni >> Nj >> cost;
        undirectedEdge* newEdge = new undirectedEdge(Ni, Nj, cost);
        kruskal.insertEdge(newEdge);

        // Step 2: Print up to the first 10 edges on the edge list to argv[3]
        kruskal.printList(kruskal.edgeListHead, 10, output2);
    }

    do
    {
        do
        {
            // Step 4: Get next min edge; discard it if Ni and Nj are equal
            nextEdge = kruskal.removeEdge();
        } while (kruskal.inWhichSet[nextEdge->Ni] == kruskal.inWhichSet[nextEdge->Nj]); // Step 5: Repeat until Ni and Nj are in different sets

        // Step 6:
        kruskal.pushEdge(nextEdge);
        kruskal.totalMSTCost += nextEdge->edgeCost;
        kruskal.merge2Sets(nextEdge->Ni, nextEdge->Nj);
        kruskal.numSets--;
        kruskal.printSet(output2);

        // Step 7:
        kruskal.printList(kruskal.MSTofG, 10, output2);
    } while (kruskal.numSets > 1); // Step 8: Repeat steps 4-7 until numSets is equal to 1

    // Step 9: output entire MST and total cost of MST to argv[2], one edge with cost per text line
    output1 << "*** A Kruskal's MST of the input graph is given below: ***\n";
    undirectedEdge* i = kruskal.MSTofG;
    while (i->next != NULL)
    {
        output1 << i->Ni << " " << i->Nj << " " << i->edgeCost << endl;
        i = i->next;
    }
    output1 << "*** The total cost of a Kruskal's MST is: " << kruskal.totalMSTCost << " ***" << endl;

    // Step 10: Close all files
    input.close();
    output1.close();
    output2.close();
}