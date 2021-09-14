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
        setLocation(300, 300);
        
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
                        textField.setText(displayText.substring(0, textField.getText().length() -1));
                        break;
                    case("C"):
                        textField.setText("");
                        break;
                    case("="):
                        EvaluateExpressions();
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
    
    private void EvaluateExpressions(){
        System.out.println("hola");
    }
    
    private void ChangeSignOfNumber(String displayText){
        Integer length1 = displayText.length() - 1;
        Integer length2 = displayText.length() - 2;
        Integer lastExp = EvaluateNumber(displayText.substring(length1));
        if(lastExp > 0){
            String signExp = displayText.substring(length2, length1);
            if(signExp.equals("-"))
                textField.setText(displayText.substring(0, length2) + lastExp);
            else
                textField.setText(displayText.substring(0, length2) + " -" + lastExp);
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
