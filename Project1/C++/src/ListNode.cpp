#include <string>
#include <iostream>
using namespace std;

struct ListNode
{
    string data;
    ListNode* next;

    ListNode(string s)
    {
        data = s;
        this->next = NULL;
    }

    ListNode(string s, ListNode* n)
    {
        data = s;
        this->next = n;
    }
};
