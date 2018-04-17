import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by farhanshafi on 10/22/16.
 */
public class pizzaGUI extends JFrame implements ActionListener {
    private static final String[] SIZE = {"Small", "Medium", "Large"};
    private static final String[] STYLE = {"Margherita", "Prosciutto", "Diavola", "Verdure", "Calzone"};
    private static final String[] TOPPINGS = {"Garlic", "Jalapenos", " extra cheese", "Bacon"};

    private JButton submitButton;
    private JRadioButton size[];
    private JComboBox dropDownList;
    private JCheckBox toppings[];
    private JTextArea output;
    private JScrollPane scrollPane;
    private boolean anyToppings[];
    private int selectedSize;

    public pizzaGUI() {
        this.setTitle("Pizza Shop");

        Container contentPane = this.getContentPane();
        contentPane.setLayout(new BorderLayout());

        //Main big panel
        JPanel leftAndrightPanel = new JPanel();
        leftAndrightPanel.setLayout(new BorderLayout());

        //Main left panel
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(3, 0));

        //size Panel
        JPanel pizzaSizePanel = new JPanel();
        pizzaSizePanel.setLayout(new FlowLayout());
        pizzaSizePanel.setBorder(BorderFactory.createTitledBorder("Select your pizza size"));
        size = new JRadioButton[SIZE.length];
        ButtonGroup group = new ButtonGroup();
        for (int i = 0; i < SIZE.length; i++) {
            size[i] = new JRadioButton(SIZE[i]);
            group.add(size[i]);
            pizzaSizePanel.add(size[i]);
        }

        optionsPanel.add(pizzaSizePanel);

        //style Panel
        JPanel pizzaStylePanel = new JPanel();
        pizzaStylePanel.setLayout(new FlowLayout());
        pizzaStylePanel.setBorder(BorderFactory.createTitledBorder("Select your pizza style"));
        dropDownList = new JComboBox(STYLE);
        dropDownList.addActionListener(this);
        pizzaStylePanel.add(dropDownList);

        optionsPanel.add(pizzaStylePanel);

        //toppings Panel
        JPanel pizzaToppingsPanel = new JPanel();
        pizzaToppingsPanel.setLayout(new FlowLayout());
        pizzaToppingsPanel.setBorder(BorderFactory.createTitledBorder("Select your pizza toppings"));
        toppings = new JCheckBox[TOPPINGS.length];
        anyToppings = new boolean[TOPPINGS.length];
        for (int i = 0; i < TOPPINGS.length; i++) {
            toppings[i] = new JCheckBox();
            toppings[i].setSelected(anyToppings[i]);
            pizzaToppingsPanel.add(toppings[i]);
            pizzaToppingsPanel.add(new JLabel(TOPPINGS[i]));
        }


        optionsPanel.add(pizzaToppingsPanel);

        //Adding 3 left panels to option panel
        optionsPanel.add(pizzaSizePanel);
        optionsPanel.add(pizzaStylePanel);
        optionsPanel.add(pizzaToppingsPanel);

        //submit button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        buttonPanel.add(submitButton);

        //Addidng 3 left to the main big panel
        leftAndrightPanel.add(optionsPanel, BorderLayout.WEST);
        leftAndrightPanel.add(buttonPanel, BorderLayout.SOUTH);

        //the output box
        JPanel outputPanel = new JPanel();
        outputPanel.setLayout(new GridLayout(0, 1));
        outputPanel.setBorder(BorderFactory.createTitledBorder("Output"));
        output = new JTextArea(20, 30);
        output.setEditable(false);
        scrollPane = new JScrollPane(output);
        outputPanel.add(scrollPane);

        //Adding output panel to right big panel
        leftAndrightPanel.add(outputPanel, BorderLayout.EAST);

        //Adding it to contentPane
        contentPane.add(leftAndrightPanel);
        this.setResizable(false);
    }

    //button helper

    private void buttonHandler(ActionEvent e) {
        Object allSources = e.getSource();
        String msg = "";
        String forToppings = "";
        String addons;
        int styleChosen;
        for (int i = 0; i < SIZE.length; i++) {
            if (size[i].isSelected()) {
                selectedSize = i;
                msg = msg + "Your order is: " + "\nYour size: " + SIZE[selectedSize] + "\n";
            }
        }
        msg = msg + "Your toppings are: ";
        for (int i = 0; i < TOPPINGS.length; i++) {
            if (toppings[i].isSelected()) {
                addons = (TOPPINGS[i] + ", ");
                msg = msg + addons;
            }
        }
        msg = msg + "\n";
        styleChosen = dropDownList.getSelectedIndex();
        String actualStyle = STYLE[styleChosen];
        msg = msg + "Your chosen style is: " + actualStyle;
        output.setText(msg);
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source instanceof JButton) {
            buttonHandler(e);
        }
    }

    public static void main(String[] args) {
        pizzaGUI example = new pizzaGUI();
        example.setVisible(true);
        example.pack();
    }
}
