import javax.swing.*;
import java.awt.*;

public class Main extends JPanel {

    private static final int CX = 400;
    private static final int CY = 300;
    private static final int R  = 150;

    private static final double START_ANGLE = Math.PI / 4;
    private static final double END_ANGLE   = 3 * Math.PI / 2;

    private static final Color START_COLOR = Color.RED;
    private static final Color END_COLOR   = Color.BLUE;

    @Override
    protected void paintComponent(Graphics gr) {
        super.paintComponent(gr);

        int x = -R;
        int y = 0;
        int error = 2 - 2 * R;

        do {
            putPixel(gr, CX - x, CY + y);
            putPixel(gr, CX - y, CY - x);
            putPixel(gr, CX + x, CY - y);
            putPixel(gr, CX + y, CY + x);

            int temp = error;
            if (temp <= y) error += ++y * 2 + 1;
            if (temp > x || error > y) error += ++x * 2 + 1;
        } while (x < 0);
    }

    private void putPixel(Graphics gr, int x, int y) {
        double angle = Math.atan2(y - CY, x - CX);
        if (angle < 0) angle += 2 * Math.PI;

        double a0 = normalizeAngle(START_ANGLE);
        double a1 = normalizeAngle(END_ANGLE);

        if (!isAngleInArc(angle, a0, a1)) return;

        double t = (angle - a0) / (a1 - a0);
        if (t < 0) t += 1;

        int red   = (int)(START_COLOR.getRed()   + t * (END_COLOR.getRed()   - START_COLOR.getRed()));
        int green = (int)(START_COLOR.getGreen() + t * (END_COLOR.getGreen() - START_COLOR.getGreen()));
        int blue  = (int)(START_COLOR.getBlue()  + t * (END_COLOR.getBlue()  - START_COLOR.getBlue()));

        gr.setColor(new Color(red, green, blue));
        gr.fillRect(x, y, 1, 1);
    }

    private double normalizeAngle(double a) {
        a %= 2 * Math.PI;
        if (a < 0) a += 2 * Math.PI;
        return a;
    }

    private boolean isAngleInArc(double a, double a0, double a1) {
        if (a0 <= a1) return a >= a0 && a <= a1;
        return a >= a0 || a <= a1;
    }

    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(800, 600);
        window.add(new Main());
        window.setVisible(true);
    }
}