#include <iostream>
#include <fstream>
#include <string>
using namespace std;

struct listBinTreeNode
{
    string chStr;
    string code;
    int prob;
    listBinTreeNode* next;
    listBinTreeNode* left;
    listBinTreeNode* right;

    listBinTreeNode()
    {
        next = NULL;
        prob = 0;
        next = NULL;
        left = NULL;
        right = NULL;
    }

    listBinTreeNode(string s, int p)
    {
        next = NULL;
        chStr = s;
        prob = p;
        next = NULL;
        left = NULL;
        right = NULL;
    }

    static void printNode(listBinTreeNode* t) { cout << t->toString() << endl; }
    
    string toString()
    {
        // ---- Return the contents of this node ----

        // Concatenate current node's chStr and prob.
        string s = "(\"" + chStr + "\", " + to_string(prob) + ", ";

        // Concatenate next node's chStr.
        if (next == NULL) s += "NULL, ";
        else              s += "\"" + next->chStr + "\", ";

        // Concatenate left node's chStr.
        if (left == NULL) s += "NULL, ";
        else              s += "\"" + left->chStr + "\", ";

        // Concatenate right node's chStr.
        if (right == NULL) s += "NULL)";
        else               s += "\"" + right->chStr + "\")";

        return s;
    }
};

struct HuffmanLinkedList 
{
    listBinTreeNode* listHead;
    listBinTreeNode* oldListHead;

    HuffmanLinkedList() { listHead = new listBinTreeNode("dummy", 0); }

    HuffmanLinkedList(listBinTreeNode* n) { listHead = n; }

    void constructHuffmanList(ifstream& input, ofstream& output5)
    {
        string chStr;
        int prob; 

        output5 << "constructHuffmanList(input, output5): \n";
        cout << "constructHuffmanList(input, output5): \n";

        while (input.good())
        {
            input >> chStr; // Read in character
            input >> prob;  // Read in probability

            // Create new node with char & prob.
            listBinTreeNode* newNode = new listBinTreeNode(chStr, prob); 
    
            // Find correct spot and insert new node between spot and spot.next. 
            listInsert(newNode->prob, newNode); 

            // Print list to console and output file.
            printList();  
            output5 << toString() << endl;    
        }
        output5 << endl; // To seperate this output from the next algorithm step.
    }

    listBinTreeNode* findSpot(int prob)
    {
        listBinTreeNode* i = listHead;
        while (i->next != NULL && prob > i->next->prob)
            i = i->next;

        return i;
    }

    void listInsert(int prob, listBinTreeNode* newNode)
    {
        listBinTreeNode* spot = findSpot(prob);
        newNode->next = spot->next;
        spot->next = newNode;
    }

    bool isEmpty() { return listHead->next == NULL; }

    void printList()
    {
        cout << toString() << endl;
    }

    string toString()
    {
        string nextString;
        string result = "listHead-->";
        for (listBinTreeNode* i = listHead; i != NULL; i = i->next)
        {
            if (i->next == NULL) nextString = "NULL)-->";
            else                 nextString = "\"" + i->next->chStr + "\")-->";
            
            result += "(\"" + i->chStr + "\", " + to_string(i->prob) + ", " + nextString;
        }
        result += "NULL";
        return result;
    }

};

struct HuffmanBinaryTree
{
    listBinTreeNode* root;

    HuffmanBinaryTree() { root = NULL; }

    void constructHuffmanBinTree(HuffmanLinkedList* list, ofstream& output5)
    {
        // Create a dummy node and oldListHead point to dummy node and save original linked list.
        list->oldListHead = new listBinTreeNode("yummy", 0);
        list->oldListHead->next = list->listHead->next;  
        
        // Move listhead from "dummy" node to first node in list.
        list->listHead = list->listHead->next; 
        listBinTreeNode* newNode;

        output5 << "constructHuffmanBinTree(list, output5): \n";
        cout << "constructHuffmanBinTree(list, output5): \n";

        // Keep going until there is one node in list. 
        while (list->listHead->next != NULL) 
        {
            string combinedChar = list->listHead->chStr + list->listHead->next->chStr;
            int combinedProb = list->listHead->prob + list->listHead->next->prob;

            // Create newNode with combined prob & char
            newNode = new listBinTreeNode(combinedChar, combinedProb); 

            newNode->left = list->listHead; // Set newNode's left to first node of the list.
            newNode->right = list->listHead->next; // Set newNode's right to second node of the list.

            // Search and insert newNode starting from where listHead currently is.
            list->listInsert(newNode->prob, newNode);   

             // Print the new node for debugging.
            listBinTreeNode::printNode(newNode);       
            output5 << newNode->toString() << endl;

            // Print the current list for debugging.
            list->printList();                          
            output5 << list->toString() << endl;

            // Advance the current list head by two pointers.
            list->listHead = list->listHead->next->next; 
        }
        // Set the root to the newly created HuffmanBinaryTree when there is only one node left.
        root = newNode; 
    }

    void preOrder(listBinTreeNode* t, ofstream& output3)
    {   
        if (t != NULL)
        {
            listBinTreeNode::printNode(t);
            output3 << t->toString() << endl;
            preOrder(t->left, output3);
            preOrder(t->right, output3);
        }
    }

    void inOrder(listBinTreeNode* t, ofstream& output4)
    {
        if (t != NULL)
        {
            inOrder(t->left, output4);
            listBinTreeNode::printNode(t);
            output4 << t->toString() << endl;
            inOrder(t->right, output4);
        }
    }

    void postOrder(listBinTreeNode* t, ofstream& output5)
    {
        if (t != NULL)
        {
            postOrder(t->left, output5);
            postOrder(t->right, output5);
            listBinTreeNode::printNode(t);
            output5 << t->toString() << endl;
        }
    }

    static bool isLeaf(listBinTreeNode* t) { return (t->left == NULL && t->right == NULL); }

    static void constructCharCode(listBinTreeNode* t, string code, ofstream& output1)
    {
        if (t == NULL)
        { 
            output1 << "This is an empty tree." << endl;
            cout << "This is an empty tree." << endl;
            exit(0); 
        }
        else if (HuffmanBinaryTree::isLeaf(t))
        {
            t->code = code;
            output1 << t->chStr << " : " << t->code << endl;
            cout << t->chStr << " : " << t->code << endl;
        }
        else
        {
            HuffmanBinaryTree::constructCharCode(t->left, code + "0", output1);
            HuffmanBinaryTree::constructCharCode(t->right, code + "1", output1);
        }
    }
};


int main(int argc, char** argv)
{
    ifstream input;
    ofstream output1, output2, output3, output4, output5;

    input.open(argv[1]);    // Open input file.
    output1.open(argv[2]);   // For Huffman pairs.
    output2.open(argv[3]);   // For pre-order traversal of Huffman binary tree.
    output3.open(argv[4]);   // For in-order traversal of Huffman binary tree.
    output4.open(argv[5]);   // For post-order traversal of Huffman binary tree.
    output5.open(argv[6]);   // For debugging outputs.

    // Create Huffman linked list with input file.
    HuffmanLinkedList* hll = new HuffmanLinkedList(); 
    hll->constructHuffmanList(input, output5); 
    listBinTreeNode newn("akjsdl", 10);

    // Create Huffman binary tree from linked list.
    HuffmanBinaryTree* hbt = new HuffmanBinaryTree();
    hbt->constructHuffmanBinTree(hll, output5);      

    // Create the code for each char.
    output1 << "constructCharCode(hbt->root, \"\", output1):\n";
    cout << "constructCharCode(hbt->root, \"\", output1):\n";
    HuffmanBinaryTree::constructCharCode(hbt->root, "", output1);  

    output2 << "preOrder: \n";
    output3 << "inOrder: \n";
    output4 << "postOrder: \n";

    cout << "preOrder: \n";
    hbt->preOrder(hbt->root, output2);
    cout << "inOrder: \n"; 
    hbt->inOrder(hbt->root, output3);
    cout << "postOrder: \n";
    hbt->postOrder(hbt->root, output4);

    input.close();
    output1.close();
    output2.close();
    output3.close();
    output4.close();
    output5.close();
    return 0;
}