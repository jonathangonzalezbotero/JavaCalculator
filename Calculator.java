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

    /**
     * Constructor of class Calculator
     */
    public Calculator()
    {
        super("My PROG5001 - Calculator (1.0) ");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(400, 400));
        
        BuildComponents();
        
        pack();
        setVisible(true);
    }
    
    /**
     * Method to create the GUI of the Calculator, using BorderLayout as the main Layout
     */
    private void BuildComponents(){
        BorderLayout calculatorPanel = new BorderLayout();
        JPanel displayPanel = new JPanel();
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        GridBagConstraints gbc1 = new GridBagConstraints();
        
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.BOTH;

        textField = new JTextField();
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

        gbc.gridwidth = 1;
        gbc.gridy = 0;
        gbc.gridx = 3;
        mainPanel.add(new ButtonCalculator("+"), gbc);
        gbc.gridy = 0;
        gbc.gridx = 4;
        mainPanel.add(new ButtonCalculator("<<"), gbc);
        gbc.gridy = 1;
        gbc.gridx = 3;
        mainPanel.add(new ButtonCalculator("-"), gbc);
        gbc.gridy = 1;
        gbc.gridx = 4;
        mainPanel.add(new ButtonCalculator("C"), gbc);
        gbc.gridy = 2;
        gbc.gridx = 3;
        mainPanel.add(new ButtonCalculator("*"), gbc);
        gbc.gridy = 2;
        gbc.gridx = 4;
        mainPanel.add(new ButtonCalculator("("), gbc);
        gbc.gridy = 3;
        gbc.gridx = 3;
        mainPanel.add(new ButtonCalculator("/"), gbc);
        gbc.gridy = 3;
        gbc.gridx = 4;
        mainPanel.add(new ButtonCalculator(")"), gbc);
        gbc.gridy = 4;
        gbc.gridx = 3;
        mainPanel.add(new ButtonCalculator("!"), gbc);
        gbc.gridy = 4;
        gbc.gridx = 4;
        mainPanel.add(new ButtonCalculator("OFF"), gbc);
        
        setLayout(calculatorPanel);
        add(displayPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
    }
    
    /**
     * Customize class for the buttons of the GUI
     */
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
                    case("OFF"):
                        System.exit(0);
                        break;
                    case("<<"):
                        textField.setForeground(Color.BLACK);
                        DeleteMostRight(displayText);
                        break;
                    case("="):
                        GetResult(displayText);
                        break;
                    case("+/-"):
                        ChangeSignOfNumber(displayText);
                        break;
                    case("C"):
                        textField.setForeground(Color.BLACK);
                        textField.setText("");
                        break;
                    default:
                        textField.setText(displayText + " " + ae.getActionCommand());
                        break;
                }
            }
        });
      }
    }
    
    /**
     * Get the result after evaluate the expression
     * @param displayText the value of the display to be evaluted
     */
    private void GetResult(String displayText){
        String value = Convert(displayText);
        if(value.equals(""))
            value = displayText;
        else
            value = Double.toString(Evaluate(value));
        
        textField.setText(value + "");
    }

    /**
     * Conversion from infix to postfix
     * @param infix - Get the expression to evaluate
     * @return the value in postfix
     */
    private String Convert(String infix) {
        Stack<String> stack = new Stack();
        String[] values = infix.trim().split(" ");
        String postfix = "";
        for (int i = 0; i < values.length; i++) {
            if(IsOperator(values[i]) >= 0){
                if(!stack.isEmpty() && IsOperator(values[i]) > 2 && IsOperator(values[i-1]) > 2){
                    HighLightError();
                    return "";
                }else{
                    switch(IsOperator(values[i])){
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
                            while (!stack.isEmpty() && (IsOperator(values[i]) <= IsOperator(stack.peek()))) {
                                postfix = postfix + stack.pop() + ",";
                            }
                            stack.push(values[i]);
                            break;
                    }
                }
            }else{
                postfix = postfix + values[i] + ",";
            }
        }
        while (!stack.isEmpty()) {
            postfix = postfix + stack.pop() + ",";
        }
        
        return postfix;
    }
    
    /**
     * Evaluate the postfox expressions in order to get the result
     * @param postfix - expression to calculate
     * @return Result of the operation
     */
    public double Evaluate(String postfix) {
        Stack<Double> stack = new Stack();
        String[] values = postfix.split(",");
        double result = 0;
        for (int i = 0; i < values.length; i++) {
            switch (IsOperator(values[i])) {
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
                    if(stack.size() > 1){
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
                    }else{
                        HighLightError();
                        break;
                    }
                    break;
                case -1:
                    stack.push(Double.parseDouble("" + values[i]));                
                    break;
            }
        }
        
        if(!stack.isEmpty())
            result = stack.pop();
        
        return result;
    }

    /**
     * According to the character check if either is an operator or not
     * @param c -Character to evaluate
     * @return The value according to the operator
     */
    private int IsOperator(String c) {
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
    
    /**
     * HighLight the display to let the user know something is wrong
     */
    private void HighLightError(){
        textField.setForeground(Color.RED);
    }
    
    /**
     * Delete the first right character of a expression
     * @param displayText - expression to delete the character
     */
    private void DeleteMostRight(String displayText){
        Integer length = displayText.length();
            
        if(displayText.substring(length - 1).equals(" "))
            displayText = displayText.substring(0, length - 2);
        else
            displayText = displayText.substring(0, length - 1);
        
        textField.setText(displayText.trim());
    }
    
    /**
     * Change the sign of a number
     * @param displayText - value to change the sign
     */
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
    
    /**
     * Check if the value given is a number or not
     * @param value - number to check
     * @return The value as a integer
     */
    private Integer EvaluateNumber(String value){
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    /**
     * Main method to run the program
     */
    public static void main (String[] args) {
        Calculator frame = new Calculator();
    }
}
