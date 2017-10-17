#include <iostream>
#include <fstream>
#include <cmath>
using namespace std;

struct Point
{
    int x, y, label, distance;

    Point() {}
    Point(int x, int y) { this->x = x; this->y = y; }
    
    static string printPoint(Point p)
    {
        return "(" + to_string(p.x) + ", " + to_string(p.y) + ", " + to_string(p.label) 
                   + ", " + to_string(p.distance) + ")";
    }
};

struct K_Mean
{
    // Nested xyCoord struct
    struct xyCoord
    {
        double x, y, label;
        xyCoord() {}
        xyCoord(int x, int y, int label) { this->x = x; this->y = y; this->label = label; }
    };

    // Properties of K_Means struct
    int K, numPts, numRow, numCol, changeLabel = 1;
    int** imageArray;
    Point* pointSet;
    xyCoord* Kcentroids;

    // Constructor
    K_Mean(int np, int k, int nr, int nc)
    {
        numPts = np; K = k; numRow = nr; numCol = nc;
        // Initialize imageArray as dynamic 2D array
        imageArray = new int*[nr];
        for (int i = 0; i < numRow; i++) imageArray[i] = new int[nc] {0};
        // Dynamically intialize pointsSet
        pointSet = new Point[np];
        // Dynamically initialize Kcentroids
        Kcentroids = new xyCoord[k];
        for (int i = 0; i < K; i++) 
        {
            Kcentroids[i]= xyCoord(0, 0, i + 1);
        }
    }

    void loadPointSet(ifstream& input)
    {
        int x, y, i = 0;
        while (!input.eof() && i < numPts)
        {
            input >> x;
            input >> y;
            pointSet[i] = Point(x, y);
            i++;
        }
    }

    void assignLabel()
    {
        // srand(time(NULL));
        for (int i = 0; i < numPts; i++)
        {
            int randomLabel = rand() % K + 1;
            pointSet[i].label = randomLabel;
        } 
    }

    void mapPoints2Image()
    {
        for (int i = 0; i < numPts; i++) imageArray[pointSet[i].y][pointSet[i].x] = pointSet[i].label;
    }

    void kMeanClustering()
    {
        int k;
        changeLabel = 0;
        int* Kcount = new int[K]{0};

        // Zero out previous iteration averages
        for (int i = 0; i < K; i++)
        {
            Kcentroids[i].x = 0;
            Kcentroids[i].y = 0;
        }

        // Add up the x, y points
        for (int i = 0; i < numPts; i++)
        {
            k = pointSet[i].label - 1;
            Kcentroids[k].x += pointSet[i].x;
            Kcentroids[k].y += pointSet[i].y;
            Kcount[k]++;
        }

        // Calculate new averages with the count of each centroid
        for (int i = 0; i < K; i++)
        {
            Kcentroids[i].x /= Kcount[i];
            Kcentroids[i].y /= Kcount[i];
        }

        // For each point in pointSet, compute the distance from each centroid 1 to k. Then, change
        // the label for that point to the label of the closest centroid.
        int minLabel;
        double d1, d2;
        for (int i = 0; i < numPts; i++)
        {
            k = pointSet[i].label - 1;
            minLabel = 0;
            d1 = sqrt(pow(pointSet[i].x - Kcentroids[0].x, 2) + pow(pointSet[i].y - Kcentroids[0].y, 2));
            for (int j = 1; j < K; j++)
            {
                d2 = sqrt(pow(pointSet[i].x - Kcentroids[j].x, 2) + pow(pointSet[i].y - Kcentroids[j].y, 2));
                if (d1 > d2)
                {
                    d1 = d2;
                    minLabel = j;
                }
            }

            // Change the label of current point if the points current label is different than the minLabel
            if (k != minLabel)
            {
                pointSet[i].label = minLabel + 1;
                changeLabel++; 
            }
        }
    }

    void printPointSet(ofstream& output1)
    {
        output1 << "final result of k-means clustering:\n";
        output1 << K << endl << numPts << endl << numRow << " " << numCol << endl; 
        for (int i = 0; i < numPts; i++)
            output1 << pointSet[i].x << " " << pointSet[i].y << " " << pointSet[i].label << endl;
    }

    void prettyPrint(ofstream& output2)
    {

        for (int r = 0; r < numRow; r++)
        {
            for (int c = 0; c < numCol; c++)
                if (imageArray[r][c] > 0)
                    output2 << imageArray[r][c];
                else   
                    output2 << " ";
            output2 << endl;
        }
    }

};

int main(int argc, char** argv)
{
    ifstream input;
    ofstream output1, output2;
    int clusterNumber, numberOfPoints, dimensionX, dimensionY; 

    input.open(argv[1]);
    output1.open(argv[2]);
    output2.open(argv[3]);

    // Read in cluster number
    input >> clusterNumber;
    // Read in number of points in the data set
    input >> numberOfPoints;
    // Read in the dimensions of the image array
    input >> dimensionX;
    input >> dimensionY;

    // Create K-mean clustering object
    K_Mean km(numberOfPoints, clusterNumber, dimensionX, dimensionY);
    
    // Read each points from input and store it into struct pointSet array
    km.loadPointSet(input);

    // Assign each point a random label between 1 to k sequentially in pointSet array
    km.assignLabel();

    output2 << "prettyPrint until changeLabel is 0:\n";

    // Keep iterating until we reach an iteration where there was no relabelling
    while (km.changeLabel > 0)
    {
        // Read each point from pointSet and its labels and map the label to imageArray
        km.mapPoints2Image(); 

        // Output to output2 the imageArray
        km.prettyPrint(output2);

        // Go through entire point in the pointSet array, compute each label's centroid, 
        // and relabel each point in pointSet to their closest centroid.
        km.kMeanClustering();
        output2 << "Next iteration: \n";
    }
    
    km.mapPoints2Image();
    km.prettyPrint(output2);
    km.printPointSet(output1);
    
    input.close();
    output1.close();
    output2.close();
}