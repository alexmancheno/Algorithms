
public class LinkedList
{
    private ListNode listHead;

    public LinkedList()
    {
        listHead = new ListNode("dummy");
    }

    public boolean isEmpty()
    {
        return listHead.next == null;
    }

    public void listInsert(String data)
    {
        ListNode i = listHead;
        ListNode newNode = new ListNode(data);
        while (i.next != null && data.compareTo(i.next.data) > 0)
        {
            i = i.next;
        }
        newNode.next = i.next;
        i.next = newNode;
    }

    public void listDelete(String data)
    {
        // Instructions state that implementation is not required for this project.
    }

    public void printList()
    {
        String a = "";
        System.out.print("listHead ");
        for (ListNode i = listHead; i != null; i = i.next)
        {
            if (i.next != null) a = i.next.data;
            else                a = "NULL";
            System.out.print("-->(" + i.data + ", " + a + ")");
        }
        System.out.println();
    }
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder("listHead ");
        String a = "";
        for (ListNode i = listHead; i != null; i = i.next)
        {
            if (i.next != null) a = i.next.data;
            else                a = "null";
            sb.append("-->(" + i.data + ", " + a + ")");
        }
        return sb.toString();
    }
}