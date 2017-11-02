#include <iostream>
#include <fstream>
using namespace std;

struct DijkstraSSS
{
    int numNodes, sourceNode, minNode, currentNode, newCost;
    int** costMatrix;
    int* fatherAry, *toDoAry, *bestCostAry;

    DijkstraSSS(int n, int s)
    {
        numNodes = n; sourceNode = s;

        // Initialize cost-matrix; set diagonal to 0, else to 99999 as infinity
        costMatrix = new int*[n + 1];
        for (int i = 0; i <= n; i++) costMatrix[i] = new int[n + 1];

        for (int r = 0; r <= n; r++)
            for (int c = 0; c <= n; c++)
                if (r != c) costMatrix[r][c] = 99999;
                else        costMatrix[r][c] = 0;

        // Initialize father-array; set each node's father to itself
        fatherAry = new int[n + 1];
        for (int i = 0; i <= n; i++) fatherAry[i] = i;

        // Initialize to-do array to 1's; 1 means the node still needs work
        toDoAry = new int[n + 1];
        for (int i = 0; i <= n; i++) toDoAry[i] = 1;
        
        // Initialize best cost array to infinity (99999)
        bestCostAry = new int[n + 1];
        for (int i = 0; i <= n; i++) bestCostAry[i] = 99999;
    }

    void loadCostMatrix(ifstream& input) 
    {
        int from, to, cost;
        while (!input.eof())
        {
            input >> from >> to >> cost;
            costMatrix[from][to] = cost;
        }
    }

    void setBestCostAry(int sourceN)
    {
        for (int i = 1; i <= numNodes; i++) 
            bestCostAry[i] = costMatrix[sourceN][i];
    }

    void setFatherAry(int sourceN)
    {
        for (int i = 1; i <= numNodes; i++)
            fatherAry[i] = sourceN;
    }

    void setToDoAry(int sourceN)
    {
        for (int i = 1; i <= numNodes; i++)
            if (i == sourceN) toDoAry[i] = 0;
            else              toDoAry[i] = 1;
    }

    int findMinNode()
    {
        int minN = 0, minC;
        for (int i = 1; i <= numNodes; i++)
        {
            if (toDoAry[i] == 1 && minN == 0)
            {
                minN = i;
                minC = bestCostAry[i];
            }
            else if (toDoAry[i] == 1 && bestCostAry[i] < minC)
            {
                minN = i;
                minC = bestCostAry[i];
            }
        }
        return minN;
    }


    int computeCost(int minN, int currentN) 
    {
        return bestCostAry[minN] + costMatrix[minN][currentN];
    } 

    void markMinNode(int minN) { toDoAry[minN] = 0; }

    void changeFather(int node, int minN) { fatherAry[node] = minN; }

    void changeCost(int node, int newC) { bestCostAry[node] = newC; }

    void debugPrint(ofstream& output2)
    {
        output2 << "source node: " << sourceNode << endl;
        output2 << "fatherAry: " << arrayToString(fatherAry) << endl;
        output2 << "bestCostAry: " << arrayToString(bestCostAry) << endl;
        output2 << "toDoAry: " << arrayToString(toDoAry) << "\n\n"; 
    }
    
    void printShortestPath(int currentN, ofstream& output1)
    {
        int runningCost = 0, father = fatherAry[currentN], child = currentNode;
        output1 << "The path from " << sourceNode << " to " << child << ": "
                << " " << child; 
        while(father != sourceNode)
        {
            runningCost += costMatrix[father][child];
            output1 << " <- " << father;
            child = father;
            father = fatherAry[child];
        }

        runningCost += costMatrix[father][child];
        output1 << " <- " << sourceNode << ": cost = " << runningCost << endl;
    }

    bool nodesAreStillLeftToDo()
    {
        for (int i = 1; i <= numNodes; i++)
            if (toDoAry[i] == 1) return true;
        return false;
    }

    void Dijkstra(ofstream& output1, ofstream& output2)
    {
        int solution = 1;

        // Keep running until there are still nodes left to do in to-do array
        while (nodesAreStillLeftToDo())
        {
            output2 << " ----- Iteration: " << solution++ << " -----\n";
            
            // Find the current minimum node from nodes still left to do,
            // and mark it as done
            minNode = findMinNode();
            markMinNode(minNode);
            debugPrint(output2);

            /*  
            For each node, check to see if the new cost is better
            than the old cost (compare bestCostAry[minNode] + costMatrix[minNode][currentNode] and bestCostAry[currentNode]). If the new cost is better,
            change node's best cost to the new cost in bestCostAry and change its
            father to the minimum node.
            */
            currentNode = 1;
            
            while (currentNode <= numNodes)
            {
                if (toDoAry[currentNode] == 1)
                {
                    newCost = computeCost(minNode, currentNode);
                    if (newCost < bestCostAry[currentNode])
                    {
                        changeCost(currentNode, newCost);
                        changeFather(currentNode, minNode);
                        debugPrint(output2);
                    }
                }
                currentNode++;
            }
        }

        // Print the shortest path + the cost of the path for each node to output1
        currentNode = 1;
        while (currentNode <= numNodes)
        {
            printShortestPath(currentNode, output1);
            currentNode++;
        }
    }

    string arrayToString(int* a)
    {
        string s = "";
        for (int i = 1; i <= numNodes; i++) s += to_string(a[i]) + " ";
        return s;
    }
};

int main(int argc, char** argv)
{
    ifstream input;
    ofstream output1, output2;
    int sourceNode, numNodes;

    input.open(argv[1]);
    output1.open(argv[2]);  // Output of single-source shortest path
    output2.open(argv[3]);  // Output for debugging

    input >> numNodes;
    input >> sourceNode;

    // Create Dijsktra object, passing in its number of nodes and source node ID
    DijkstraSSS dijkstra(numNodes, sourceNode);

    // Load the cost matrix from input file to dijkstra object
    dijkstra.loadCostMatrix(input);

    // Initially make current best cost from source to each node using cost matrix
    dijkstra.setBestCostAry(sourceNode);

    // Set all to source node ID in father array
    dijkstra.setFatherAry(sourceNode);

    // Set source node to 0, all other nodes to 1 in to-do array
    dijkstra.setToDoAry(sourceNode);

    // Begin Dijkstra's algorithm
    dijkstra.Dijkstra(output1, output2);

    input.close();
    output1.close();
    output2.close();
    return 0;
}