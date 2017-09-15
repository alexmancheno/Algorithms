import java.io.*;
import java.util.Scanner;

class listBinTreeNode
{
    public String chStr;
    public int prob;
    public listBinTreeNode next;
    public listBinTreeNode left;
    public listBinTreeNode right;

    public listBinTreeNode()
    {
        chStr = null;
        prob = 0;
    }

    public listBinTreeNode(String s, int p)
    {
        chStr = s;
        prob = p;
    }

    // Needs to be finished.
    public String printNode(listBinTreeNode t)
    {
        String m = "(" + chStr + " : " + prob + ")" ;
        String n;

        return m; 
    }
}

class HuffmanLinkedList 
{
    public listBinTreeNode listHead;
    public listBinTreeNode oldListHead;

    public HuffmanLinkedList()
    {
        listHead = new listBinTreeNode("dummy", 0);
    }

    public void constructHuffmanList()
    {

    }

    public listBinTreeNode findSpot(int prob)
    {
        listBinTreeNode i = listHead;
        while (i.next != null && prob > i.next.prob)
        {
            i = i.next;
        }
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
            if (i.next == null) nextString = "NULL)-->";
            else                nextString = i.next.chStr + ")-->";
            System.out.print("(\"" + i.chStr + "\", " + i.prob + ", " + nextString);
        }
        System.out.print("NULL\n");
    }
}

class HuffmanBinaryTree 
{
    class charInfo
    {
        
    }
}

public class Main
{
    public static void main(String[] argv)
    {
        HuffmanLinkedList hll = new HuffmanLinkedList();
        hll.listInsert(10, new listBinTreeNode("a", 10));
        hll.listInsert(10, new listBinTreeNode("b", 10));
        hll.listInsert(10, new listBinTreeNode("c", 5));

        hll.printList();
    }
}