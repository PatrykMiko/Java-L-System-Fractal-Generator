package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * A custom JPanel responsible for rendering the L-System fractal.
 * It handles coordinate transformations to ensure the fractal perfectly fits the window.
 */
public class DrawPanel extends JPanel {
    Scene scene = new Scene();
    LSystem ls;

    public DrawPanel(LSystem ls) {
        this.ls = ls;
        setBackground(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Generate the fractal lines only once to save CPU resources
        if (scene.lines.isEmpty()) {
            String commands = ls.rewrite();
            scene.render(commands, ls);
            System.out.printf("Wygenerowano %d linii\n", scene.lines.size());
        }

        double w = getWidth();
        double h = getHeight();

        // 1. Move the origin to the center of the panel
        g2.translate(w / 2.0, h / 2.0);

        // 2. Rotate by -90 degrees so the fractal "grows" upwards instead of to the right
        g2.rotate(-Math.PI / 2);

        Rectangle2D bounds = scene.getBounds();
        int margin = 20;

        // Calculate the scaling factors needed to fit the fractal within the panel's dimensions
        double scaleX = (h - 2 * margin) / bounds.getWidth();
        double scaleY = (w - 2 * margin) / bounds.getHeight();

        // Maintain the aspect ratio by choosing the smaller scale
        double scale = Math.min(scaleX, scaleY);

        // Apply scaling (the Y-axis is inverted to match standard Cartesian coordinates)
        g2.scale(scale, -scale);

        // 3. Center the geometric bounds of the fractal at the origin
        double cx = bounds.getCenterX();
        double cy = bounds.getCenterY();
        g2.translate(-cx, -cy);

        // Adjust the stroke width so it doesn't get scaled up and become too thick
        g2.setStroke(new BasicStroke((float) (1.0 / scale)));

        // Draw all generated lines with their respective colors
        for (int i = 0; i < scene.lines.size(); i++) {
            if (i < scene.colors.size()) {
                g2.setColor(scene.colors.get(i));
            } else {
                g2.setColor(Color.WHITE); // Fallback color
            }
            g2.draw(scene.lines.get(i));
        }
    }
}