import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

/**
 * Assessment 2 PROG5001 - A2
 * Basic Java Calculator using GUI
 *
 * @author JonathanGonzalezBotero
 * @version 1.0
 */
public class Calculator extends JFrame
{
    JTextField textField;
    //
    String expression = "";

    /**
     * Constructor of class Calculator
     */
    public Calculator()
    {
        super("My PROG5001 - Calculator (1.0) ");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(400, 400));
        //setLocation(300, 300);
        
        BuildComponents();
        
        pack();
        setVisible(true);
    }
    
    private void BuildComponents(){
        BorderLayout calculatorPanel = new BorderLayout();
        JPanel displayPanel = new JPanel();
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        GridBagConstraints gbc1 = new GridBagConstraints();
        
        gbc.weighty = 0.1;
        gbc1.weighty = 0.1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc1.fill = GridBagConstraints.BOTH;

        textField = new JTextField("");
        textField.setPreferredSize(new Dimension(380, 40));
        textField.setHorizontalAlignment(JTextField.RIGHT);
        textField.setEditable(false);
        displayPanel.add(textField);

        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                gbc.gridy = i;
                gbc.gridx = j;
                mainPanel.add(new ButtonCalculator(""+((i*3)+j+1)), gbc);
            }
        }
        
        gbc.gridy = 3;
        gbc.gridx = 0;
        mainPanel.add(new ButtonCalculator("+/-"), gbc);
        gbc.gridy = 3;
        gbc.gridx = 1;
        mainPanel.add(new ButtonCalculator("0"), gbc);
        gbc.gridy = 3;
        gbc.gridx = 2;
        mainPanel.add(new ButtonCalculator("."), gbc);
        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        mainPanel.add(new ButtonCalculator("="), gbc);

        gbc1.gridy = 0;
        gbc1.gridx = 3;
        mainPanel.add(new ButtonCalculator("+"), gbc1);
        gbc1.gridy = 0;
        gbc1.gridx = 4;
        mainPanel.add(new ButtonCalculator("<<"), gbc1);
        gbc1.gridy = 1;
        gbc1.gridx = 3;
        mainPanel.add(new ButtonCalculator("-"), gbc1);
        gbc1.gridy = 1;
        gbc1.gridx = 4;
        mainPanel.add(new ButtonCalculator("C"), gbc1);
        gbc1.gridy = 2;
        gbc1.gridx = 3;
        mainPanel.add(new ButtonCalculator("*"), gbc1);
        gbc1.gridy = 2;
        gbc1.gridx = 4;
        mainPanel.add(new ButtonCalculator("("), gbc1);
        gbc1.gridy = 3;
        gbc1.gridx = 3;
        mainPanel.add(new ButtonCalculator("/"), gbc1);
        gbc1.gridy = 3;
        gbc1.gridx = 4;
        mainPanel.add(new ButtonCalculator(")"), gbc1);
        gbc1.gridy = 4;
        gbc1.gridx = 3;
        mainPanel.add(new ButtonCalculator("!"), gbc1);
        gbc1.gridy = 4;
        gbc1.gridx = 4;
        mainPanel.add(new ButtonCalculator("OFF"), gbc1);
        
        setLayout(calculatorPanel);
        add(displayPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
    }
    
    public class ButtonCalculator extends JButton {
      public ButtonCalculator(String actionCommand) {
        super(actionCommand);
        setActionCommand(actionCommand);
        if(actionCommand.equals("OFF")){
            this.setBackground(Color.ORANGE);
            this.setOpaque(true);
        }
            
        addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent ae) {
                String displayText = textField.getText();
                switch(ae.getActionCommand()){
                    case("<<"):
                        textField.setForeground(Color.BLACK);
                        DeleteMostRight(displayText);
                        break;
                    case("C"):
                        textField.setForeground(Color.BLACK);
                        textField.setText("");
                        break;
                    case("="):
                        GetResult(displayText);
                        break;
                    case("+/-"):
                        ChangeSignOfNumber(displayText);
                        break;
                    case("OFF"):
                        System.exit(0);
                        break;
                    default:
                        textField.setText(displayText + " " + ae.getActionCommand());
                        break;
                }
            }
        });
      }
    }
    
    private void GetResult(String displayText){
        String value = convert(displayText);
        System.out.println(value+ "");
        if(value.equals(""))
            value = displayText;
        else
            value = Double.toString(evaluate(value));
        
        textField.setText(value + "");
    }

    private String convert(String infix) {
        Stack<String> stack = new Stack();
        String[] values = infix.split(" ");
        String postfix = "";
        for (int i = 1; i < values.length; i++) {
            if(isOperator(values[i]) >= 0){
                /*if(!stack.isEmpty() && isOperator(stack.peek()) >= 0){
                System.out.println(i+"i");
                    textField.setForeground(Color.RED);
                    return "";
                }else{*/
                    switch(isOperator(values[i])){
                        case 0:
                            stack.push(values[i]);
                            break;
                        case 1:
                            stack.push(values[i]);
                            break;
                        case 2:
                            while (!stack.isEmpty() && !stack.peek().equals("(")) {
                                postfix = postfix + stack.pop() + ",";
                            }
                            stack.pop();
                            break;
                        default:
                            while (!stack.isEmpty() && (isOperator(values[i]) <= isOperator(stack.peek()))) {
                                postfix = postfix + stack.pop() + ",";
                            }
                            stack.push(values[i]);
                            break;
                    }
                //}
            }else{
                postfix = postfix + values[i] + ",";
            }
                System.out.println(stack+"stack");
                System.out.println(postfix+"postfix");
                System.out.println(values[i]+"values");
        }
        while (!stack.isEmpty()) {
            postfix = postfix + stack.pop() + ",";
        }
        
        return postfix;
    }
    
    public double evaluate(String postfix) {
        Stack<Double> stack = new Stack();
        String[] values = postfix.split(",");
        double result = 0;
        for (int i = 0; i < values.length; i++) {
            switch (isOperator(values[i])) {
                case 0:
                    double operand = Double.parseDouble("" + stack.pop());
                    result = 1;
                    for(int j=1; j<=operand; j++){
                        result *= j;
                    }
                    stack.push(result);
                    break;
                case 3:
                case 4:
                    double operand2 = Double.parseDouble("" + stack.pop());
                    double operand1 = Double.parseDouble("" + stack.pop());
                    if (values[i].equals("+")) {
                        result = operand1 + operand2;
                    } else
                    if (values[i].equals("-")) {
                        result = operand1 - operand2;
                    } else
                    if (values[i].equals("*")) {
                        result = operand1 * operand2;
                    } else
                    if (values[i].equals("/")) {
                        result = operand1 / operand2;
                    }
                    stack.push(result);
                    break;
                case -1:
                    stack.push(Double.parseDouble("" + values[i]));                
                    break;
            }
        }
        
        result = stack.pop();
        
        return result;
    }

    private int isOperator(String c) {
        switch (c) {
            case "!":
                return 0;
            case "(":
                return 1;
            case ")":
                return 2;
            case "+":                
            case "-":
                return 3;
            case "*":
            case "/":
                return 4;
        }
        return -1;
    }
    
    private void DeleteMostRight(String displayText){
        Integer length = displayText.length();
            
        if(displayText.substring(length - 1).equals(" "))
            displayText = displayText.substring(0, length - 2);
        else
            displayText = displayText.substring(0, length - 1);
        
        textField.setText(displayText.trim());
    }
    
    private void ChangeSignOfNumber(String displayText){
        Integer length = displayText.length();
        if(length > 0){
            Integer lastExp = EvaluateNumber(displayText.substring(length - 1));
            if(lastExp > 0){
                String signExp = displayText.substring(length - 2, length - 1);
                if(signExp.equals("-"))
                    textField.setText(displayText.substring(0, length - 2) + lastExp);
                else
                    textField.setText(displayText.substring(0, length - 2) + " -" + lastExp);
            }
        }
    }
    
    private Integer EvaluateNumber(String value){
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    public static void main (String[] args) {
        Calculator frame = new Calculator();
    }
}
