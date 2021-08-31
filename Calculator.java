import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

/**
 * Assessment 2 PROG5001 - A2
 * Basic Java Calculator using GUI
 *
 * @author JonathanGonzalezBotero
 * @version 1.0
 */
public class Calculator extends JFrame implements ActionListener, KeyListener 
{
    JPanel redPanel;
    JPanel greenPanel;
    JPanel bluePanel;
    //
    JButton btnOne;
    JButton btnTwo;
    JButton btnThree;
    //
    JTextField textField;
    //
    String expression = "";

    /**
     * Constructor of class Calculator
     */
    public Calculator()
    {
        super("My PROG5001 - Calculator (1.0) ");
        setLayout(new GridLayout(5, 5));
        //create panels
        
        textField = new JTextField();
        textField.setEditable(false);
        add(textField);
        
        add(new JButton("1"));add(new JButton("2"));add(new JButton("3"));
        add(new JButton("+"));add(new JButton("<<"));
        
        add(new JButton("4"));add(new JButton("5"));add(new JButton("6"));
        add(new JButton("-"));add(new JButton("C"));
        
        add(new JButton("7"));add(new JButton("8"));add(new JButton("9"));
        add(new JButton("*"));add(new JButton("("));
        
        add(new JButton("+/-"));add(new JButton("0"));add(new JButton("."));
        add(new JButton("/"));add(new JButton(")"));
        
        add(new JButton("="));
        add(new JButton("/"));add(new JButton(")"));
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 300));
        pack();
        
        setFocusable(true);
        addKeyListener(this);
    }
    
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (cmd.equals("CMD_One")) {
           expression = expression + "1";
        } else
        if (cmd.equals("CMD_Two")) {
           expression = expression + "2";
        } else
        if (cmd.equals("CMD_Three")) {
           expression = expression + "3";
        }
        textField.setText(expression);       
    }
    
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_BACK_SPACE)
            System.out.println("Backspace was pressed: " + e.getKeyCode());
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }
    
    public static void main (String[] args) {
        Calculator frame = new Calculator();
        frame.setVisible(true);
    }
}
