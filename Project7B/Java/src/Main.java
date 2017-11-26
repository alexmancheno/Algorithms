import java.util.Scanner;
import java.io.*;

class undirectedEdge
{
    public int Ni;
    public int Nj;
    public int edgeCost;
    public undirectedEdge next;

    public undirectedEdge(int i, int j, int e)
    {
        Ni = i; Nj = j; edgeCost = e; next = null;
    }

    public void printEdge(FileWriter output)
    {
        // output.write(toString());
    }

    @Override
    public String toString()
    {
        return "<" + Integer.toString(Ni) + ", " + Integer.toString(Nj) + ", " + Integer.toString(edgeCost) + ">";
    }
}

class PrimMST
{
    public int numNodes;
    public int[] inWhichSet;
    public undirectedEdge edgeListHead;
    public undirectedEdge MSTofG;
    public int totalMSTCost;
    
    public PrimMST(int n)
    {
        numNodes = n; totalMSTCost = 0;

        inWhichSet = new int[n + 1];

        // 1 = setA, 2 = setB. Initialize inWhichSet[1] = 1, all others to 2
        inWhichSet[1] = 1;
        for (int i = 2; i <= n; i++) inWhichSet[i] = 2;

        // Set edgeListHead and MSTofG to point to dummy nodes
        edgeListHead = new undirectedEdge(0, 0, 0);
        MSTofG = new undirectedEdge(0, 0, 0); 
    }

    public void insertEdge(undirectedEdge edge)
    {
        undirectedEdge i = edgeListHead;
        while (i.next != null && edge.edgeCost > i.next.edgeCost)
            i = i.next;
        edge.next = i.next;
        i.next = edge;
    }

    // Look for the next miniumum cost edge that connects a node in set A to a node in set B
    public undirectedEdge removeEdge()
    {
        undirectedEdge i = edgeListHead, j = i.next;
        while (inWhichSet[j.Ni] == inWhichSet[j.Nj])
        {
            i = j;
            j = j.next;
        }
        
        i.next = i.next.next;

        return j;
    }

    public void pushEdge(undirectedEdge edge)
    {
        edge.next = MSTofG;
        MSTofG = edge;
    }

    public void moveToSetA(int node) { inWhichSet[node] = 2; }

    public void printSet(FileWriter output)
    {
        try 
        {
            output.write("inWhichSet: ");
            for (int i = 1; i <= numNodes; i++)
                output.write(inWhichSet[i] + " ");
            output.write(System.lineSeparator());
        }
        catch (Exception e)
        {
            Main.printError(e);
        }
    }

    public void printList(undirectedEdge listHead, int n, FileWriter output)
    {
        try
        {
            output.write("listHead -> "); 
            undirectedEdge i = listHead;
            while (i != null && n > 0)
            {
                output.write(i.toString() + " -> ");
                i = i.next;
                n--;
            }
            output.write("NULL " + System.lineSeparator());
        }
        catch (Exception e)
        {
            Main.printError(e);
        }
    }

    public boolean setBIsEmpty()
    {
        for (int i = 1; i <= numNodes; i++)
            if (inWhichSet[i] == 2) return false;
        return true;
    }
}

public class Main
{
    public static void main(String[] argv)
    {
        try
        {
            Scanner input = new Scanner(new FileReader(argv[0])); // Open input file
            FileWriter output1 = new FileWriter(argv[1]);  // Output for the MST
            FileWriter output2 = new FileWriter(argv[2]);  // Output for debugging purposes

            int i, j, cost, n;
            undirectedEdge nextEdge;

            // Step 0:
            n = input.nextInt();
            PrimMST prim = new PrimMST(n);

            // Steps 1-3:
            output2.write("Steps 1-3 (reading from file): " + System.lineSeparator());
            while (input.hasNext())
            {
                i = input.nextInt();
                j = input.nextInt();
                cost = input.nextInt();
                prim.insertEdge(new undirectedEdge(i, j, cost));

                prim.printList(prim.edgeListHead, 10, output2);
            }

            // Step 4-8:
            output2.write("Steps 4-8 (finding Prim's MST): " + System.lineSeparator());
            while (!prim.setBIsEmpty())
            {
                // Step 4-5:
                nextEdge = prim.removeEdge();

                // Step 6:
                prim.pushEdge(nextEdge);
                prim.totalMSTCost += nextEdge.edgeCost;

                // Make sure both Ni and Nj are now in set A
                prim.inWhichSet[nextEdge.Ni] = 1;
                prim.inWhichSet[nextEdge.Nj] = 1;    
                    
                prim.printSet(output2);
                
                // Step 7:
                prim.printList(prim.MSTofG, 10, output2);
            }

            // Step 9:
            undirectedEdge e = prim.MSTofG;
            output1.write("*** The Prim's MST of the input graph is given below: ***" + System.lineSeparator());
            while (e.next != null)
            {
                output1.write(e.Ni + " " + e.Nj + " " + e.edgeCost + System.lineSeparator());
                e = e.next;
            }
            output1.write("*** The total cost of the Prim's MST is: " + prim.totalMSTCost + " ***" + System.lineSeparator());

            // Step 10:
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