import java.io.*;
import java.util.Scanner;

class listBinTreeNode
{
    public String chStr;
    public String code;
    public int prob;
    public listBinTreeNode next;
    public listBinTreeNode left;
    public listBinTreeNode right;

    public listBinTreeNode()
    {
        next = null;
        prob = 0;
        chStr = "";
        next = null;
        left = null;
        right = null;
    }

    public listBinTreeNode(String s, int p)
    {
        next = null;
        chStr = s;
        prob = p;
        next = null;
        left = null;
        right = null;
    }

    public static void printNode(listBinTreeNode t) {  System.out.println(t); }

    @Override
    public String toString()
    {
        // ---- Return the contents of this node ----
        String s = "t = { 'chStr' : ";

        // Concatenate current node's chStr and prob.
        s += "'" + chStr + "', 'prob' : " + prob + ", ";

        // Concatenate next node's chStr.
        s += "'next->chStr' : ";
        if (next == null) s += "null, ";
        else                    s += "'" + next.chStr + "', ";

        // Concatenate left node's chStr.
        s += "'left->chStr' : ";
        if (left == null) s += "null, ";
        else                    s += "'" + left.chStr + "', ";

        // Concatenate right node's chStr.
        s += "'right->chStr' : ";
        if (right == null) s += "null }";
        else                     s += "'" + right.chStr + "' }";

        return s;
    }
}

class HuffmanLinkedList 
{
    public listBinTreeNode listHead;
    public listBinTreeNode oldListHead;

    public HuffmanLinkedList() { listHead = new listBinTreeNode("dummy", 0); }

    public void constructHuffmanList(Scanner input, FileWriter output5)
    {
        String chStr;
        int prob;

        try
        {
            output5.write("constructHuffmanList(intput, output5): \n");

            while (input.hasNext())
            {
                chStr = input.next();  // Read in character
                prob = input.nextInt();  // Read in probability

                listBinTreeNode newNode = new listBinTreeNode(chStr, prob);

                // Find correct spot and insert new node between spot and spot.next. 
                listInsert(newNode.prob, newNode); 

                // Print list to console and output file.
                printList();
                output5.write(this.toString());
            }
            output5.write('\n');
        }
        catch (Exception e)
        {
            Main.printError(e);
        }
    }

    public listBinTreeNode findSpot(int prob)
    {
        listBinTreeNode i = listHead;
        while (i.next != null && prob > i.next.prob)
            i = i.next;

        return i;
    }

    public void listInsert(int prob, listBinTreeNode newNode)
    {
        listBinTreeNode spot = findSpot(prob);
        newNode.next = spot.next;
        spot.next = newNode;
    }

    public boolean isEmpty() { return listHead.next == null; }

    public void printList()
    {
        String nextString;
        System.out.print("listHead-->");
        for (listBinTreeNode i = listHead; i != null; i = i.next)
        {
            if (i.next == null) nextString = "null)-->";
            else                nextString = i.next.chStr + ")-->";
            System.out.print("(\"" + i.chStr + "\", " + i.prob + ", " + nextString);
        }
        System.out.print("null\n");
    }

    @Override
    public String toString()
    {
        String nextString;
        String result = "listHead-->";
        for (listBinTreeNode i = listHead; i != null; i = i.next)
        {
            if (i.next == null) nextString = "null)-->";
            else                 nextString = "\"" + i.next.chStr + "\")-->";
            
            result += "(\"" + i.chStr + "\", " + i.prob + ", " + nextString;
        }
        result += "null";
        return result;
    }
}

class HuffmanBinaryTree 
{
    listBinTreeNode root;
    
    HuffmanBinaryTree() { root = null; }

    void constructHuffmanBinTree(HuffmanLinkedList list, FileWriter output5)
    {
        // Create a dummy node and oldListHead point to dummy node and save original linked list.
        list.oldListHead = new listBinTreeNode("yummy", 0);
        list.oldListHead.next = list.listHead.next;  
        
        // Move listhead from "dummy" node to first node in list.
        list.listHead = list.listHead.next; 
        listBinTreeNode newNode = null;

        try
        {
            output5.write("constructHuffmanBinTree(list, output5): \n");

            // Keep going until there is one node in list. 
            while (list.listHead.next != null) 
            {
                String combinedChar = list.listHead.chStr + list.listHead.next.chStr;
                int combinedProb = list.listHead.prob + list.listHead.next.prob;

                // Create newNode with combined prob & char
                newNode = new listBinTreeNode(combinedChar, combinedProb); 

                newNode.left = list.listHead; // Set newNode's left to first node of the list.
                newNode.right = list.listHead.next; // Set newNode's right to second node of the list.

                // Search and insert newNode starting from where listHead currently is.
                list.listInsert(newNode.prob, newNode);   

                // Print the new node for debugging.
                listBinTreeNode.printNode(newNode);       
                output5.write(newNode.toString() + '\n');

                // Print the current list for debugging.
                list.printList();                          
                output5.write(list.toString() + '\n');

                // Advance the current list head by two pointers.
                list.listHead = list.listHead.next.next; 
            }
            // Set the root to the newly created HuffmanBinaryTree when there is only one node left.
            root = newNode; 
        }
        catch (Exception e)
        {
            Main.printError(e);
        }
    }

    void preOrder(listBinTreeNode t, FileWriter output3)
    {   
        if (t != null)
        {
            listBinTreeNode.printNode(t);
            try
            { 
                output3.write(t.toString() + '\n');
            }
            catch (Exception e)
            {
                Main.printError(e);
            }
            preOrder(t.left, output3);
            preOrder(t.right, output3);
        }
    }

    void inOrder(listBinTreeNode t, FileWriter output4)
    {
        if (t != null)
        {
            inOrder(t.left, output4);
            listBinTreeNode.printNode(t);
            try
            { 
                output4.write(t.toString() + '\n');
            }
            catch (Exception e)
            {
                Main.printError(e);
            }
            inOrder(t.right, output4);
        }
    }

    void postOrder(listBinTreeNode t, FileWriter output5)
    {
        if (t != null)
        {
            postOrder(t.left, output5);
            postOrder(t.right, output5);
            listBinTreeNode.printNode(t);
            try
            { 
                output5.write(t.toString() + '\n');
            }
            catch (Exception e)
            {
                Main.printError(e);
            }
        }
    }

    static boolean isLeaf(listBinTreeNode t) { return (t.left == null && t.right == null); }
    
        static void constructCharCode(listBinTreeNode t, String code, FileWriter output1)
        {
            try
            {
                if (t == null)
                { 
                    output1.write("This is an empty tree." + '\n');
                    System.out.println("This is an empty tree." + '\n');
                    System.exit(0); 
                }
                else if (HuffmanBinaryTree.isLeaf(t))
                {
                    t.code = code;
                    output1.write(t.chStr + " : " + t.code + '\n');
                    System.out.println(t.chStr + " : " + t.code + '\n');
                }
                else
                {
                    HuffmanBinaryTree.constructCharCode(t.left, code + "0", output1);
                    HuffmanBinaryTree.constructCharCode(t.right, code + "1", output1);
                }
            }
            catch (Exception e)
            {
                Main.printError(e);
            }
        }
}

public class Main
{
    public static void main(String[] argv)
    {
        try 
        {
            Scanner input = new Scanner(new FileReader(argv[0])); // Open input file.
            FileWriter output1 = new FileWriter(argv[1]);   // For Huffman pairs.
            FileWriter output2 = new FileWriter(argv[2]);   // For pre-order traversal of Huffman binary tree.
            FileWriter output3 = new FileWriter(argv[3]);  // For in-order traversal of Huffman binary tree.
            FileWriter output4 = new FileWriter(argv[4]);   // For post-order traversal of Huffman binary tree.
            FileWriter output5 = new FileWriter(argv[5]);  // For debugging outputs.
        
            // Create Huffman linked list with input file.
            HuffmanLinkedList hll = new HuffmanLinkedList(); 
            hll.constructHuffmanList(input, output5); 
        
            // Create Huffman binary tree from linked list.
            HuffmanBinaryTree hbt = new HuffmanBinaryTree();
            hbt.constructHuffmanBinTree(hll, output5);      
        
            // Create the code for each char.
            HuffmanBinaryTree.constructCharCode(hbt.root, "", output1);  
        
            output2.write("preOrder: \n");
            output3.write("inOrder: \n");
            output4.write("postOrder: \n");
        
            hbt.preOrder(hbt.root, output2); 
            hbt.inOrder(hbt.root, output3);
            hbt.postOrder(hbt.root, output4);
        
            input.close();
            output1.close();
            output2.close();
            output3.close();
            output4.close();
            output5.close();
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