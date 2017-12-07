import java.util.Scanner;
import java.io.*;

class Node
{
    public int ID,  Color, numEdges;
    public Node next;

    public Node(int id)
    {
        ID = id; Color = 0; numEdges = 0; next = null;
    }
}

class graphColoring
{
    public int[][] adjacencyMatrix;
    public Node[] nodeArray; // To keep track of the nodes, their edges, and their colors.
    public Node listHead;
    public int newColor, numNode;

    public graphColoring(int n)
    {
        numNode = n;

        adjacencyMatrix = new int[n + 1][];
        for (int i = 0; i <= n; i++) adjacencyMatrix[i] = new int[n + 1];

        nodeArray = new Node[n + 1];

        for (int i = 0; i <= n; i++) nodeArray[i] = new Node(i);

        listHead = new Node(0);
    }

    public void loadMatrix(Scanner input)
    {
        int i, j;
        while (input.hasNext())
        {
            i = input.nextInt();
            j = input.nextInt();
            adjacencyMatrix[i][j]++;
            adjacencyMatrix[j][i]++;

            nodeArray[i].numEdges++;
        }
    }

    public void insertOneNode(Node newNode)
    {
        Node i = listHead;
        while (i.next != null && newNode.numEdges > i.next.numEdges)
            i = i.next;
        newNode.next = i.next;
        i.next = newNode;
    }

    public void constructNodeList()
    {
        for (int i = 1; i <= numNode; i++)
            insertOneNode(nodeArray[i]);
    }

    // This method should return 0 if there are no adjacent nodes with the same color
    public int checkAdjacent(int nodeID, int color)
    {
        for (int i = 1; i <= numNode; i++)
            if (adjacencyMatrix[nodeID][i] > 0 && nodeArray[i].Color == color)
                return 1;
        return 0;
    }

    public void printAdjacencyMatrix(FileWriter output)
    {
        try 
        {
            output.write("0  |");
            for (int i = 1; i <= numNode; i++)
                if (i < 10) output.write(i + "  ");
                else        output.write(i + " ");
            output.write(System.lineSeparator());
            output.write("------------------------------------------------------------");
            output.write(System.lineSeparator());
            for (int i = 1; i <= numNode; i++)
            {
                if (i < 10)
                    output.write(i + "  |");
                else
                    output.write(i + " |");
                for (int j = 1; j <= numNode; j++)
                    output.write(adjacencyMatrix[i][j] + "  ");

                output.write(System.lineSeparator());
            }
            output.write(System.lineSeparator());
        }
        catch (Exception e)
        {
            Main.printError(e);
        }
    }

    public boolean nodesStillNeedColoring()
    {
        for (int i = 1; i <= numNode; i++)
            if (nodeArray[i].Color == 0) return true;
        return false;
    }
}

public class Main
{
    public static void main(String[] argv)
    {
        try
        {
            Scanner input = new Scanner(new FileReader(argv[0]));
            FileWriter output = new FileWriter(argv[1]);

            Scanner input2 = new Scanner(new FileReader("input.txt"));
            //Step 0:
            int numNode = input.nextInt(), newColor = 0;
            graphColoring graph = new graphColoring(numNode);;
            Node currentNode;

            graph.loadMatrix(input);

            graph.printAdjacencyMatrix(output);

            graph.constructNodeList(); 


            // Steps 1-6
            while (graph.nodesStillNeedColoring())
            {
                currentNode = graph.listHead.next;
                newColor++;
                // Steps 2-4:
                while (currentNode != null)
                {
                    if (currentNode.Color == 0 && graph.checkAdjacent(currentNode.ID, newColor) == 0)
                    {
                        graph.adjacencyMatrix[currentNode.ID][currentNode.ID] = newColor;
                        currentNode.Color = newColor;
                    }
                    currentNode = currentNode.next;
                }

                // Step 5:
                graph.printAdjacencyMatrix(output);
            }

            // Step 7:
            output.write("newColor after the algorithm: " + Integer.toString(newColor) + System.lineSeparator());
            output.write("adjacencyMatrix after the algorithm:" + System.lineSeparator() + System.lineSeparator());
            graph.printAdjacencyMatrix(output);

            // Step 8:
            input.close();
            output.close();
        }
        catch(Exception e)
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