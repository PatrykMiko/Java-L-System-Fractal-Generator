# Java L-System Fractal Generator

A Java Swing application that generates and renders complex procedural fractals based on **Lindenmayer Systems (L-Systems)**. 



By defining an initial axiom and a set of production rules, this application utilizes **Turtle Graphics logic** and string rewriting to draw intricate geometric patterns, such as Fractal Trees, Koch Snowflakes, and Sierpinski Triangles.

## 🚀 Key Features

* **JSON Configuration:** Define your L-System rules, iteration count, angles, and custom colors entirely via an external JSON file. No need to recompile to test new fractals!
* **Turtle Graphics Interpretation:** Implements standard L-System control characters:
  * `F` (or any letter): Move forward and draw a line.
  * `+` / `-`: Rotate by a specific angle.
  * `[`: Push current position and angle to the stack (start a branch).
  * `]`: Pop position and angle from the stack (end a branch).
* **Dynamic Auto-Scaling:** Uses affine transformations (`Graphics2D.scale`, `translate`) to calculate the bounding box of the generated fractal and automatically scale/center it perfectly within the window.
* **Custom Rendering:** Supports mapping specific L-System characters to specific colors (e.g., green for leaves, brown for trunks) using simple strings or hex codes.

## 🛠️ Technology Stack
* **Java 17+**
* **Java Swing & AWT (`Graphics2D`)** for UI and hardware-accelerated 2D rendering.
* **Gson (Google)** for JSON configuration parsing.

## ⚙️ Configuration Example (`choinka.json`)

To run the application, ensure you have a JSON file named `choinka.json` in your working directory. Here is an example of a simple Fractal Tree configuration:

```json
{
  "name": "Fractal Tree (Choinka)",
  "axiom": "X",
  "rules": {
    "X": "F+[[X]-X]-F[-FX]+X",
    "F": "FF"
  },
  "stepLength": 1.0,
  "angleIncrement": 25.0,
  "iterations": 6,
  "allLetterForward": true,
  "symbolColors": {
    "X": "green",
    "F": "#8B4513"
  }
}
