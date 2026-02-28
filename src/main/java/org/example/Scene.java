package org.example;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Stack;
import java.util.List;

/**
 * Interprets the generated L-System string using Turtle Graphics logic
 * and stores the resulting geometric lines and colors for rendering.
 */
public class Scene {
    List<Line2D> lines = new ArrayList<>();
    List<Color> colors = new ArrayList<>();

    void addLine(double x1, double y1, double x2, double y2, Color color) {
        lines.add(new Line2D.Double(x1, y1, x2, y2));
        colors.add(color);
    }

    /**
     * Calculates the bounding box of the entire generated fractal.
     * Used for dynamic scaling and centering on the screen.
     */
    Rectangle2D getBounds(){
        if (lines.isEmpty()) {
            return new Rectangle2D.Double(0, 0, 0, 0);
        }

        Rectangle2D bounds = lines.get(0).getBounds2D();

        for (int i = 1; i < lines.size(); i++) {
            bounds = bounds.createUnion(lines.get(i).getBounds2D());
        }
        return bounds;
    }

    /**
     * Interprets the L-System commands and converts them into physical lines.
     * Implements a state stack to handle fractal branching (using '[' and ']').
     */
    void render(String commands, LSystem ls) {
        lines.clear();
        colors.clear();

        double step = ls.getStepLength();
        double angleIncrement = ls.getAngleIncrement();
        boolean allLettersForward = ls.isAllLetterForward();

        double x = 0;
        double y = 0;
        double angle = 0;

        // Stack to save position and angle for branching
        Stack<double[]> stack = new Stack<>();

        double radStep = Math.toRadians(angleIncrement);

        for (char c : commands.toCharArray()) {
            switch (c) {
                case '+':
                    angle += radStep; // Turn left/right
                    break;
                case '-':
                    angle -= radStep; // Turn right/left
                    break;
                case '[':
                    // Save the current state (position and angle) to the stack
                    stack.push(new double[]{x, y, angle});
                    break;
                case ']':
                    // Restore the previous state from the stack (end of a branch)
                    if (!stack.isEmpty()) {
                        double[] state = stack.pop();
                        x = state[0];
                        y = state[1];
                        angle = state[2];
                    }
                    break;
                default:
                    // Check if the current character implies a forward drawing motion
                    boolean shouldDraw = (c == 'F');
                    if (!shouldDraw && allLettersForward) {
                        shouldDraw = Character.isLetter(c);
                    }

                    if (shouldDraw) {
                        // Calculate the new coordinate using trigonometry
                        double x2 = x + step * Math.cos(angle);
                        double y2 = y + step * Math.sin(angle);

                        Color color = ls.getColorForSymbol(c);

                        addLine(x, y, x2, y2, color);

                        // Move the "turtle" to the new position
                        x = x2;
                        y = y2;
                    }
                    break;
            }
        }
    }
}
