import java.util.Scanner;
import java.io.*;
class testRead {
  public static void main(String[] args) {
    System.out.println("hello world");
    
    try{
      Scanner reader = new Scanner(new FileReader("test.txt")).useDelimiter("\\s");
      String i = reader.next();
      int price, stock;
      int x = 0;
      while (i != null && x < 10)
      {
        System.out.print(i + " ");  
        if (i.equals("R"))
        {
            stock = reader.next(); // convert to int
            price = reader.next(); //convert to double
            Inventory n = new Inventory(stock, price);
        }
        else if (i.equals("S"))
        {
            // pop from stack 
        }
        System.out.println();  
        i = reader.nextLine();
        x++;
      }

    }
    catch (Exception e)
    {
      
    }
    
  }
}