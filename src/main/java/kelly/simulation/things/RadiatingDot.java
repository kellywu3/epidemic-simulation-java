package kelly.simulation.things;

import java.awt.*;
import java.awt.image.BufferedImage;

public class RadiatingDot implements Animatable {
    private Color color;
    private int coreRadius;
    private int maxRadius;
    private int radioThickness;
    private BufferedImage[] imageFrames;

    public RadiatingDot(Color color, int coreRadius, int maxRadius, int radioThickness, int frames) {
        this.color = color;
        this.coreRadius = coreRadius;
        this.maxRadius = maxRadius;
        this.radioThickness = radioThickness;
        imageFrames = new BufferedImage[frames];
    }

    @Override
    public Image getFrame(int frameIndex) {
        int index = frameIndex % imageFrames.length;
        if(index < 0) {
            index += imageFrames.length;
        }
        BufferedImage result = imageFrames[index];
        if(result == null) {
            result = actuallyDrawIt(index);
            imageFrames[index] = result;
        }
        return result;
    }

    private BufferedImage actuallyDrawIt(int index) {
        int rr = coreRadius + (index + 1) * (maxRadius - coreRadius) / imageFrames.length;
        int fr = rr + (radioThickness/2 + 1);
        BufferedImage buf = new BufferedImage(fr * 2, fr * 2, BufferedImage.TYPE_INT_ARGB);
        Graphics g = buf.getGraphics();

        int a = 255 * (imageFrames.length - index - 1) / imageFrames.length;
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);

        g2.setStroke(new BasicStroke(radioThickness));
        g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), a));

        int p = fr - rr;

        g.drawOval(p,p, rr * 2, rr * 2);

        g.setColor(color);
        int cx = fr - coreRadius;
        int cd = 2 * coreRadius + 1;
        g.fillOval(cx, cx, cd, cd);

        return buf;
    }
}
