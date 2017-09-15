#include <iostream>
#include <cmath>
using namespace std;

bool isOk(int q[], int c)
{
    for (int i = 0; i < c; i++)
        if (q[i] == q[c] || c - i == abs(q[c] - q[i]))
            return false;
    return true;
}

void backtrack(int q[], int &c)
{
    q[c] = 0;
    c--;
    if (c >= 0)
    {
        q[c]++;
        if (q[c] == 8) backtrack(q, c);
    }
}

void print(int q[])
{
    for (int i = 0; i < 8; i++)
        cout << q[i] << " ";
    cout << endl;
}

int main()
{
    int q[8] = {0};
    int c = 0;
    int solutionNumber = 1;
    while (c >= 0)
    {
        if (q[c] == 8)
            backtrack(q, c);
        else if (c == 8)
        {
            // print(q);
            cout << "Solution " << solutionNumber << endl;
            solutionNumber++;
            c--;
            q[c]++;
        }
        else if (isOk(q, c))
            c++;
        else
            q[c]++;
    }
    return 0;
}