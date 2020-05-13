package kelly.simulation.ui;

import javax.swing.*;
import java.awt.event.ActionListener;

public class LabeledInput extends JPanel {

    private JTextField value;

    public LabeledInput(String description, int inputSize, int initValue) {
        this(description, inputSize, Integer.toString(initValue));
    }

    public LabeledInput(String description, int inputSize, double initValue) {
        this(description, inputSize, Double.toString(initValue));
    }

    public LabeledInput(String description, int inputSize, String initValue) {
        value = new JTextField(inputSize);
        value.setText(initValue);
        add(new JLabel(description));
        add(value);
    }

    public void addActionListener(ActionListener l) {
        value.addActionListener(l);
    }

    public int getIntValue() {
        return Integer.parseInt(value.getText());
    }

    public double getDoubleValue() {
        return Double.parseDouble(value.getText());
    }

    public void setValue(int val) {
        value.setText(Integer.toString(val));
    }

    public void setValue(double val) {
        value.setText(Double.toString(val));
    }
}
