import java.awt.GridLayout;
import javax.swing.*;

public class Main
{
    public static void main(String[] args)
    {
        JFrame gui = new JFrame("My gui");
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setSize(300, 300);
        gui.setLayout(new GridLayout(3, 3));

        gui.setVisible(true);

        // JLabel 

        JTextArea textArea = new JTextArea(5, 20);
        textArea.setText("Hello, world");
        JScrollPane scrollPane = new JScrollPane(textArea);
        gui.getContentPane().add(scrollPane);

        JTextArea textArea1 = new JTextArea(5, 20);
        textArea1.setText("Testing");
        JScrollPane scrollPane1 = new JScrollPane(textArea1);
        gui.getContentPane().add(scrollPane1);

        JTextArea textArea2 = new JTextArea(5, 20);
        textArea2.setText("The third test");
        JScrollPane scrollPane2 = new JScrollPane(textArea2);
        gui.getContentPane().add(scrollPane2);
    }
}