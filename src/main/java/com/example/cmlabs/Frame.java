package com.example.cmlabs;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Frame extends JPanel {
  private final int preferredWidth;
  private final int preferredHeight;
  private final int borderGap;
  private final int xStrokeCount;
  private final int yStrokeCount;
  private final double xMax;
  private final double yMax;
  private final List<Function<Double, Double>> functions;
  private List<String> functionNames = new ArrayList<>();

  public Frame(int preferredWidth, int preferredHeight, int borderGap, int xStrokeCount, int yStrokeCount,
               double xMax, double yMax, List<Function<Double, Double>> functions) {
    this.preferredWidth = preferredWidth;
    this.preferredHeight = preferredHeight;
    this.borderGap = borderGap;
    this.xStrokeCount = xStrokeCount;
    this.yStrokeCount = yStrokeCount;
    this.xMax = xMax;
    this.yMax = yMax;
    this.functions = functions;
  }

  public Frame(int preferredWidth, int preferredHeight, int borderGap, int xStrokeCount, int yStrokeCount,
               double xMax, double yMax, Map<String, Function<Double, Double>> namedFunctions) {
    this.preferredWidth = preferredWidth;
    this.preferredHeight = preferredHeight;
    this.borderGap = borderGap;
    this.xStrokeCount = xStrokeCount;
    this.yStrokeCount = yStrokeCount;
    this.xMax = xMax;
    this.yMax = yMax;
    this.functions = new ArrayList<>(namedFunctions.values());
    this.functionNames = new ArrayList<>(namedFunctions.keySet());
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(preferredWidth, preferredHeight);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = configGraphics(g);

    HatchedAxes hatchedAxes = new HatchedAxes(g2, borderGap, 12, getWidth(), getHeight(), xMax, yMax);
    hatchedAxes.draw(xStrokeCount, yStrokeCount);

    Graph graph = new Graph(g2, borderGap, xMax, yMax, getWidth(), getHeight());
    functions.forEach(graph::draw);

    if (!functionNames.isEmpty()) {
      FunctionsLabel functionsLabel = new FunctionsLabel(g2, borderGap);
      functionsLabel.draw(functionNames);
    }
  }

  private Graphics2D configGraphics(Graphics graphics) {
    Graphics2D g2 = (Graphics2D)graphics;
    g2.setFont(new Font("", Font.PLAIN, 12));
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setStroke(new BasicStroke(1f));
    return g2;
  }

  public static void createAndShowGui(Frame mainPanel, String frameName) {
    JFrame frame = new JFrame(frameName);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().add(mainPanel);
    frame.pack();
    frame.setLocationByPlatform(true);
    frame.setVisible(true);
  }

  public static void createAndShowGui(Frame mainPanel) {
    JFrame frame = new JFrame("Frame");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().add(mainPanel);
    frame.pack();
    frame.setLocationByPlatform(true);
    frame.setVisible(true);
  }

  public static void main(String[] args) {
    Function<Double, Double> labFunction1 = x -> 1 / x;
    Function<Double, Double> labFunction2 = x -> (5 * x - 12) / (Math.pow(x, 2) + 11 * x + 20);
    Function<Double, Double> labFunction3 = x -> Math.sin(x) / Math.sqrt(x - 4);
    Function<Double, Double> labFunction4 = x -> x + 4;
    Function<Double, Double> labFunction5 = x -> x + 5;
    Function<Double, Double> labFunction6 = x -> x + 6;
    Function<Double, Double> labFunction7 = x -> x + 7;
    List<Function<Double, Double>> functions = List.of(labFunction1, labFunction2, labFunction3, labFunction4, labFunction5, labFunction6, labFunction7);
    Frame mainPanel = new Frame(800, 800, 30, 20, 60, 20, 20, functions.stream().collect(Collectors.toMap(Object::toString, function -> function)));
    SwingUtilities.invokeLater(() -> createAndShowGui(mainPanel));
  }
}
