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
            int[] charCounts = new int[256];
            int index;
            char charIn;

            reader.useDelimiter("");
            while (reader.hasNext())
            {
                charIn = reader.next().charAt(0);
                index = (char)charIn;
                if (index < 256 && index != 8 && index != 9 && index != 10 && index != 13 && index != 11 && index != 32 && index != 160)
                {
                    charCounts[index]++;
                }
            }

            printAry(charCounts, writer);
            reader.close();
            writer.close();

        }
        catch (Exception e)
        {
            printError(e);
        }
    }

    public static void printAry(int[] charCounts, FileWriter writer)
    {
        String s;
        for (int i = 0; i < 256; i++)
        {
            if (charCounts[i] != 0)
            {
                s = "Char" + i + " \'"+ (char)i + "\' # " + charCounts[i] + "\n";
                System.out.print(s);
                try 
                {
                    writer.write(s);
                }
                catch(Exception e)
                {
                    printError(e);
                }
            }
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