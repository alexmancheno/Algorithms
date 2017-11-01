import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.lang.*;

public class warmUp {
    public static void main(String[] args) {
      try {
        File file = new File("input.txt");
            Scanner msg = new Scanner(file);
            while (msg.hasNextLine()) {
                String line = msg.nextLine();
                // System.out.println(line);
                if (line.length()>0 && line.charAt(0) == 'E') {
                    int col = Character.getNumericValue(line.charAt(1));
                    line = line.substring(2);
                    int row = (int) Math.ceil(line.length()/(double) col);
                    char E[][] = new char[row][col];
                    int n = 0;

                    for (int i = 0; i < row; i++) {
                        for (int j = 0; j < col; j++) {
                            if (line.length() > n) {
                                E[i][j] = line.charAt(n);
                                n++;
                            }
                            if (line.length() <= n) {
                                E[i][j] = 'Z';
                                n++;
                            }
                        }
                    }

                    String Encrypt="";

                    for (int j=0; j < col; j++){
                        for (int i=0; i < row; i++ ){
                            Encrypt += E[i][j];
                        }

                    }
                    System.out.println(Encrypt);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}