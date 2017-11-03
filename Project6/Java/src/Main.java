import java.io.*;
import java.util.Scanner;

class DijkstraSSS
{
    public int numNodes, sourceNode, minNode, currentNode, newCost;
    public int[][] costMatrix;
    public int[] fatherAry, toDoAry, bestCostAry;

    public DijkstraSSS(int n, int s)
    {
        numNodes = n; sourceNode = s;

        // Initialize cost-matrix; set diagonal to 0, else to 99999 as infinity
        costMatrix = new int[n + 1][n + 1];
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

    public void loadCostMatrix(Scanner input)
    {
        int from, to, cost;
        while (input.hasNext())
        {
            from = input.nextInt();
            to = input.nextInt();
            cost = input.nextInt();
            costMatrix[from][to] = cost;
        }

        for (int r = 1; r <= numNodes; r++)
        {
            for (int c = 1; c <= numNodes; c++)
            {
                System.out.print(costMatrix[r][c] + " ");
            }
            System.out.println();
        }
    }

    public void setBestCostAry(int sourceN)
    {
        for (int i = 1; i <= numNodes; i++) 
            bestCostAry[i] = costMatrix[sourceN][i];
    }

    public void setFatherAry(int sourceN)
    {
        for (int i = 1; i <= numNodes; i++)
            fatherAry[i] = sourceN;
    }

    public void setToDoAry(int sourceN)
    {
        for (int i = 1; i <= numNodes; i++)
            if (i == sourceN) toDoAry[i] = 0;
            else              toDoAry[i] = 1;
    }

    public int findMinNode()
    {
        int minN = 0, minC = 0;
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

    public int computeCost(int minN, int currentN) 
    {
        return bestCostAry[minN] + costMatrix[minN][currentN];
    } 

    public void markMinNode(int minN) { toDoAry[minN] = 0; }

    public void changeFather(int node, int minN) { fatherAry[node] = minN; }

    public void changeCost(int node, int newC) { bestCostAry[node] = newC; }

    public void debugPrint(FileWriter output2)
    {
        try
        {
            output2.write("source node: " + sourceNode + '\n');
            output2.write("fatherAry: " + arrayToString(fatherAry) + '\n');
            output2.write("bestCostAry: " + arrayToString(bestCostAry) + '\n');
            output2.write("toDoAry: " + arrayToString(toDoAry) + "\n\n");
        }
        catch(Exception e)
        {
            Main.printError(e);
        }
    }


    public boolean nodesAreStillLeftToDo()
    {
        for (int i = 1; i <= numNodes; i++)
            if (toDoAry[i] == 1) return true;
        return false;
    }

    public void printShortestPath(int currentN, FileWriter output1)
    {
        try 
        {
            int runningCost = 0, father = fatherAry[currentN], child = currentNode;
            output1.write("The path from " + sourceNode + " to " + child + ": "
                    + " " + child); 
            while(father != sourceNode)
            {
                runningCost += costMatrix[father][child];
                output1.write(" <- " + father);
                child = father;
                father = fatherAry[child];
            }

            runningCost += costMatrix[father][child];
            output1.write(" <- " + sourceNode + ": cost = " + runningCost + '\n');
        }
        catch(Exception e)
        {
            Main.printError(e);
        }
    }

    void Dijkstra(FileWriter output1, FileWriter output2)
    {
        int solution = 1;

        // Keep running until there are still nodes left to do in to-do array
        while (nodesAreStillLeftToDo())
        {
            try 
            {
                output2.write(" ----- Iteration: " + solution++ + " -----\n");
            }
            catch (Exception e)
            {
                Main.printError(e);
            }
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

    public String arrayToString(int[] a)
    {
        String s = "";
        for (int i = 1; i <= numNodes; i++) s += a[i] + " ";
        return s;
    }
}

public class Main
{
    public static void main(String[] argv)
    {
        try
        {
            Scanner input = new Scanner(new FileReader(argv[0]));
            FileWriter output1 = new FileWriter(argv[1]);
            FileWriter output2 = new FileWriter(argv[2]);

            int numNodes = input.nextInt(), sourceNode = input.nextInt();

            // Create Dijsktra object, passing in its number of nodes and source node ID
            DijkstraSSS dijkstra = new DijkstraSSS(numNodes, sourceNode);
    
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

        }
        catch (Exception e)
        {
            printError(e);
        }
    }

    // For debugging purposes.
    public static void printError(Exception e) 
    {
        StringWriter writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        e.printStackTrace(printWriter);
        printWriter.flush();
        String stackTrace = writer.toString();
        System.out.println(stackTrace);
    }
}