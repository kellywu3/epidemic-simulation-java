package kelly.simulation.ui;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;

public class NumberDisplay extends JLabel {
    private int width;

    public NumberDisplay(int width) {
        this.width = width;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, 16);
    }

    public void setValue(int value) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        setText(nf.format(value));
    }
}
