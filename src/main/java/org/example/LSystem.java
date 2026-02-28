package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents the mathematical model of a Lindenmayer system (L-System).
 * Stores the axiom, rules, and rendering properties (angles, colors, iterations).
 */
public class LSystem {

    String name = "unknown";
    String axiom = "";
    Map<Character, String> rules = new HashMap<>();
    double stepLength = 1.0;
    double angleIncrement = 90;
    int iterations = 1;
    boolean allLetterForward = true;

    Map<Character, String> symbolColors = new HashMap<>();

    // Standard Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAxiom() { return axiom; }
    public void setAxiom(String axiom) { this.axiom = axiom; }
    public double getStepLength() { return stepLength; }
    public void setStepLength(double stepLength) { this.stepLength = stepLength; }
    public double getAngleIncrement() { return angleIncrement; }
    public void setAngleIncrement(double angleIncrement) { this.angleIncrement = angleIncrement; }
    public int getIterations() { return iterations; }
    public void setIterations(int iterations) { this.iterations = iterations; }
    public boolean isAllLetterForward() { return allLetterForward; }
    public void setAllLetterForward(boolean allLetterForward) { this.allLetterForward = allLetterForward; }

    /**
     * Adds a production rule to the L-System.
     */
    public void addRule(char lhs, String rhs) {
        rules.put(lhs, rhs);
    }

    public void addRule(String lhs, String rhs) {
        if (lhs == null || lhs.length() != 1) {
            throw new RuntimeException("Rule key must be a single character.");
        }
        addRule(lhs.charAt(0), rhs);
    }

    public void addSymbolColor(char symbol, String colorName) {
        symbolColors.put(symbol, colorName);
    }

    /**
     * Resolves the Java Color object based on the symbol's configured color name or HEX code.
     */
    public Color getColorForSymbol(char symbol) {
        String colorName = symbolColors.get(symbol);
        if (colorName == null) return Color.WHITE;

        if (colorName.startsWith("#")) {
            try {
                return Color.decode(colorName);
            } catch (NumberFormatException e) {
                return Color.WHITE;
            }
        }

        switch (colorName.toLowerCase()) {
            case "red": return Color.RED;
            case "green": return Color.GREEN;
            case "blue": return Color.BLUE;
            case "yellow": return Color.YELLOW;
            case "orange": return Color.ORANGE;
            case "gray": return Color.GRAY;
            case "white": return Color.WHITE;
            default: return Color.WHITE;
        }
    }

    /**
     * Executes the rewriting process for a given number of iterations.
     * Replaces characters based on the defined production rules.
     */
    public String rewrite(int iterations) {
        String current = getAxiom();
        for (int i = 0; i < iterations; i++) {
            StringBuilder next = new StringBuilder();
            for (char c : current.toCharArray()) {
                if (rules.containsKey(c)) {
                    next.append(rules.get(c));
                } else {
                    next.append(c); // Constants remain unchanged
                }
            }
            current = next.toString();
        }
        return current;
    }

    public String rewrite() {
        return rewrite(iterations);
    }

    String toJson() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

    static LSystem fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, LSystem.class);
    }
}
