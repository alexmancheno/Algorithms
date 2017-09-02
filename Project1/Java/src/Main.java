import java.io.*;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args) 
    {

        try 
        {
            Scanner reader = new Scanner(new FileReader(args[0]));
            FileWriter writer = new FileWriter(args[1]);
            
            LinkedList ll = new LinkedList();
            System.out.println(ll);
            writer.write(ll.toString() + "\n");

            String s;
            while (reader.hasNext())
            {
                s = reader.next();
                ll.listInsert(s);
                ll.printList();
                writer.write(ll.toString() + "\n");
            }
   
            reader.close();
            writer.close();
        }
        catch(Exception e)
        {
            printError(e);
        }

    }

    // For debugging purposes.
    public static void printError(Exception e) 
    {
        StringWriter writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        e.printStackTrace(printWriter);
        printWriter.flush();
        String stackTrace = writer.toString();
        System.out.println(stackTrace);
    }
}