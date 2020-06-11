package kelly.simulation.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LabeledInput extends JPanel {
    private JLabel label;
    private JTextField value;
    private int inputSize;

    public LabeledInput(String description, int inputSize, int initValue) {
        this(description, inputSize, Integer.toString(initValue));
        value.setHorizontalAlignment(SwingConstants.RIGHT);
    }

    public LabeledInput(String description, int inputSize, double initValue) {
        this(description, inputSize, Double.toString(initValue));
        value.setHorizontalAlignment(SwingConstants.RIGHT);
    }

    public LabeledInput(String description, int inputSize, String initValue) {
        this.inputSize = inputSize;
        value = new JTextField(inputSize);
        value.setText(initValue);
        label = new JLabel(description);
        add(label);
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

    private Dimension internalSize() {
        Dimension dl = label.getMinimumSize();
        Dimension dv = value.getMaximumSize();
        int w = dl.width + inputSize * 18;
        int h = 26
                // Math.max(dl.height, dv.height)
           ;
        return new Dimension(w, h);
    }

    @Override
    public Dimension getMaximumSize() {
        return internalSize();
    }

    @Override
    public Dimension getPreferredSize() {
        return internalSize();
    }
}
