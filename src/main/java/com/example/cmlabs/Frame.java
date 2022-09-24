package com.example.cmlabs;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

public class Frame extends JPanel {
  private final int preferredWidth;
  private final int preferredHeight;
  private final int borderGap;
  private final int xStrokeCount;
  private final int yStrokeCount;
  private final List<Function<Double, Double>> functions;

  public Frame(int preferredWidth, int preferredHeight, int borderGap, int xStrokeCount, int yStrokeCount, List<Function<Double, Double>> functions) {
    this.preferredWidth = preferredWidth;
    this.preferredHeight = preferredHeight;
    this.borderGap = borderGap;
    this.xStrokeCount = xStrokeCount;
    this.yStrokeCount = yStrokeCount;
    this.functions = functions;
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(preferredWidth, preferredHeight);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = configGraphics(g);

    double xMax = 20;
    double yMax = 19;

    HatchedAxes hatchedAxes = new HatchedAxes(g2, borderGap, 12, getWidth(), getHeight(), xMax, yMax);
    hatchedAxes.draw(xStrokeCount, yStrokeCount);

    Graph graph = new Graph(g2, borderGap, xMax, yMax, getWidth(), getHeight());
    functions.forEach(graph::draw);
  }

  private Graphics2D configGraphics(Graphics graphics) {
    Graphics2D g2 = (Graphics2D)graphics;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setStroke(new BasicStroke(1f));
    return g2;
  }

  public static void createAndShowGui(List<Function<Double, Double>> functions) {
    Frame mainPanel = new Frame(800, 800, 30, 20, 20, functions);
    JFrame frame = new JFrame("Frame");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().add(mainPanel);
    frame.pack();
    frame.setLocationByPlatform(true);
    frame.setVisible(true);
  }

  public static void main(String[] args) {
    Function<Double, Double> labFunction1 = x -> 1/x;
    Function<Double, Double> labFunction2 = x -> (5 * x - 12)/(Math.pow(x, 2) + 11 * x + 30);
    Function<Double, Double> labFunction3 = x -> Math.log1p(x - 1);
    SwingUtilities.invokeLater(() -> Frame.createAndShowGui(List.of(
      labFunction1, labFunction2, labFunction3)
    ));
  }
}
