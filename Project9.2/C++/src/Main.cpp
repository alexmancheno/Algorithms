#include <iostream>
#include <fstream>
using namespace std;

struct Node
{
    int colorID;
    Node* next;
    
    Node(int c) { colorID = c; next = NULL; }
};

struct graphColoring
{
    int** adjacencyMatrix;
    int newColor, numNode;
    Node* usedColorTop;

    graphColoring(int n)
    {   
        numNode = n;
        adjacencyMatrix = new int*[n + 1];
        for (int i = 0; i <= n; i++)
            adjacencyMatrix[i] = new int[n + 1]{0};

        usedColorTop = NULL;
    }

    void loadMatrix(ifstream& input)
    {
        int i, j;
        while(!input.eof())
        {
            input >> i >> j;
            adjacencyMatrix[i][j]++;
            adjacencyMatrix[j][i]++;
        }
    }

    // Returns 0 if there is no adjacent node using that color, 1 otherwise
    int checkAdjacent(int node, int color)
    {
        for (int i = 1; i <= numNode; i++)
            if (adjacencyMatrix[node][i] > 0 && adjacencyMatrix[i][i] == color) return 1;
        return 0;
    }

    void pushUsedColor(Node* n)
    {
        if (usedColorTop == NULL)
        {
            cout << "Top was null!!\n";
            usedColorTop = n;
        }
        else
        {
            cout << "Top was not null, pushing: " << n->colorID << endl;
            n->next = usedColorTop;
            usedColorTop = n;
        }
    }

    int findUsedColor(int currentNode)
    {
        int color = 0;
        Node* i = usedColorTop;
        cout << "using findUsedColor for: " << currentNode << endl;
        while(i != NULL)
        {
            cout << "Did we get this far?" << endl;
            if (checkAdjacent(currentNode, i->colorID) == 0)
                return i->colorID;
            i = i->next;
            cout << "looping!" << endl;
        }

        return color;
    }

    bool nodesStillNeedColoring()
    {
        for (int i = 1; i <= numNode; i++)
            if (adjacencyMatrix[i][i] == 0)
                return true;
        return false;
    }

    void printAdjacencyMatrix(ofstream& output)
    {
        output << "0 |";
        for (int i = 1; i <= numNode; i++)
            if (i < 10) output << i << "  ";
            else        output << i << " ";
        output << endl;
        output << "-----------------------------------------------------------\n";
        for (int i = 1; i <= numNode; i++)
        {
            if (i < 10) output << i << "  |";
            else        output << i << " |";

            for (int j = 1; j <= numNode; j++)
                output << adjacencyMatrix[i][j] << "  ";
            output << endl;
        }
        output << endl;
    }

};

int main(int argc, char** argv)
{
    ifstream input;
    ofstream output;
    int numberOfNodes, newColor, currentNode, usedColor;
   

    input.open(argv[1]);
    output.open(argv[2]);
    

    // Step 0:
    input >> numberOfNodes;
    cout << "number of nodes: " << numberOfNodes << endl;
    graphColoring graph(numberOfNodes);

    graph.loadMatrix(input);
    newColor = 0;
    currentNode = 0;

    // Step 1-3:
    while(graph.nodesStillNeedColoring())
    {
        // Step 1:
        currentNode++;
        graph.printAdjacencyMatrix(output);

        // Step 2:
        cout << "currentNode: " << currentNode << endl;
        usedColor = graph.findUsedColor(currentNode);
        cout << "usedColor: " << usedColor << endl;
        if (usedColor > 0)
        {
            graph.adjacencyMatrix[currentNode][currentNode] = usedColor;
        }
        else
        {
            newColor++;
            graph.adjacencyMatrix[currentNode][currentNode] = newColor;
            graph.pushUsedColor(new Node(newColor));
        }
    }

    // Step 4:
    output << "The total number of colors used is: " << newColor << endl;
    graph.printAdjacencyMatrix(output);

    // Step 5:
    input.close();
    output.close();
}