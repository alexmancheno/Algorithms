#include "ListNode.cpp"

struct LinkedList
{
    ListNode* head;

    LinkedList()
    {
        head = new ListNode("dummy");
    }

    bool isEmpty()
    {
        return head->next == NULL;
    }

    void listInsert(string s)
    {
        ListNode* i = head;
        ListNode* newListNode = new ListNode(s);

        while (i->next != NULL && i->next->data.compare(s) < 0)
        {
            i = i->next;
        }

        newListNode->next = i->next;
        i->next = newListNode;
    }

    void listDelete(string s)
    {
        // Instructions state that implementation is not required for this project.
    }

    void printList()
    {
        cout << "listHead";
        string a = "";
        for (ListNode* i = head; i != NULL; i = i->next)
        {
            if (i->next != NULL) a = i->next->data;
            else                 a = "NULL";
            cout << "-->(" + i->data + ", " + a + ")";
        }
        cout << endl;
    }

    string toString()
    {
        string s = "listHead ";
        string a = "";
        for (ListNode* i = head; i != NULL; i = i->next)
        {
            if (i->next != NULL) a = i->next->data;
            else                 a = "NULL";
            s += "-->(" + i->data + ", " + a + ")";
        }
        return s;
    }
};