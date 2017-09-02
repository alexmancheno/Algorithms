
public class ListNode
{
    public String data;
    public ListNode next;

    public ListNode()
    {
        this(null);
    }

    public ListNode(String e)
    {
        data = e;
        next = null;
    }

    @Override
    public String toString() { return data; }
}