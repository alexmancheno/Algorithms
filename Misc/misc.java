import java.util.*;

public class misc
{

    // Write a method that returns the maximum sum from a line of comma seperated integers
    // From a string:
    public static int largestSum(String line)
    {
      String[] a = line.split(" *,* ");
      int largestSum = Integer.parseInt(a[0]);
      int num = 0, sum = largestSum;
      
      for (int i = 1; i < a.length; i++)
      {
        num = Integer.parseInt(a[i]);
        sum = sum >= 0 ? num + sum : num;
        largestSum = largestSum >= sum ? largestSum : sum;
      }
      return largestSum;
    }

    /* A person has taken a loan of $6000.00 for a fixed annual interest rate of 6% for 5 years with no down payment. The
    monthly payment has been fixed at $116.00 for entire term of the loan. Here is the formula to calculate monthly fixed
    payment: (NOTE: See attachment)
    
    P = (monthly rate * Loan amount) / (1 - (1+monthly interest rate)^-n) Here n is no of payment periods.

    Write a program to: 1. To calculate monthly payment 2. To print out monthly payment and total interest payment for the
    duration of loan rounded to its nearest integer
    */

    public static String calculateMonthlyPayments(String line)
    {
      String[] a = line.split("~");
      double loanAmount = Double.parseDouble(a[0]);
      double paymentPeriods = Double.parseDouble(a[1]) * 12;
      double interestRate = Double.parseDouble(a[2])/100;
      double monthlyRate = interestRate / 12;
      double downPayment = Double.parseDouble(a[3]);
      
      double monthlyPayment = Math.round((monthlyRate * (loanAmount - downPayment)) /
                              (1 - Math.pow(1+monthlyRate, paymentPeriods * -1)) * 100.0) / 100.0;
                              
      int totalInterest = (int)((monthlyPayment * paymentPeriods - (loanAmount - downPayment)) + 0.5);
      String result = "$" + String.valueOf(monthlyPayment) + "~$" + String.valueOf(totalInterest);
      
      
      return result;
    }
    /**
     * Minimize side bar
Challenge Tab - Active
 Test Case Output Tab
Programming Challenge Description:
NAV is the Net Assets Valuation which is the value of the total assets held in a portfolio. Often the portfolio managers try to track a certain benchmark portfolio in terms of the percentage weight of the assets. For instance, consider a portfolio PORT with 3 assets and a benchmark portfolio BENCH with 3 assets:

    PORT                    BENCH
Stock   Qty   Price     Stock     Qty     Price
AXN     10      10        AXN     50      10
BGT     20      30        BGT     30      30
CXZ     10      30        DFG     30      20
The percentage Nav in stock A for PORT can be calculated as follows:

Nav(PORT) = (Qty(AXN) * Price(AXN))+(Qty(BGT) * Price(BGT))+(Qty(CXZ) * Price(CXZ))

%Nav  = (Qty(AXN) * Price(AXN)) * 100 / Nav(PORT)
A Portfolio is said to be overweight in a stock if its %Nav in that stock is larger than the %Nav in the stock in the Benchmark. Alternately, it is underweight in a stock if its %Nav in a stock is less than the benchmark.

Write a program to calculate the difference in the %Nav of the holdings in the PORT and the BENCH.

Sort the portfolios in alphabetical order and display the difference upto 2 decimal points.


Input:
PORT:AXN,10,10;BGT,20,30;CXZ,10,30|BENCH:AXN,50,10;BGT,30,30;DFG,30,20


Output:
AXN:-15.0,BGT:15.0,CXZ:30.0,DFG:-30.0


Test 1
Test Input Download Test InputPORT:AXN,0,10;BGT,20,30;CXZ,10,30|BENCH:AXN,50,10;BGT,30,30;DFG,30,20;XYZ,0,10
Expected Output Download Test OutputAXN:-25.00,BGT:21.67,CXZ:33.33,DFG:-30.00,XYZ:-0.00
Test 2
Test Input Download Test InputPORT:AXN,10,10;BGT,20,30;CXZ,10,30|BENCH:AXN,50,10;BGT,30,30;DFG,30,20
Expected Output Download Test OutputAXN:-15.00,BGT:15.00,CXZ:30.00,DFG:-30.00
     */

    public static String NAV(String line)
    {
      String[] a = line.split("[:|]");
      // System.out.println(a[0]);
      System.out.println(a[1]);
      // System.out.println(a[2]);
      System.out.println(a[3]);
      
      // Calculate NAV for each stock in PORT
      String[] PORT = a[1].split(";");
      String[] BENCH = a[3].split(";");
      HashMap<String, Integer> difference = new HashMap<String, Integer>();
      
      int portNav = Nav(PORT);
      for (int i = 0; i < PORT.length; i++)
      {
        String[] stock = PORT[i].split(",");
        String stockName = stock[0];
        int qty = Integer.parseInt(stock[1]);
        int price = Integer.parseInt(stock[2]);
        difference.put(PORT[0], (qty * price) * 100 / portNav);
      }
      
      return "";
    }
    
    private static int Nav(String[] a)
    {
      int nav = 0;
      
      for (int i = 0; i < a.length; i)
      {
        String[] stock = 
        nav += Integer.parseInt(a[i+1]) * Integer.parseInt(a[i+2]);
      }
      return nav;
    }
}