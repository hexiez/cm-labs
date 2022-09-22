package com.example.cmlabs;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Frame extends JPanel {
  private final int preferredWidth;
  private final int preferredHeight;
  private final int borderGap;
  private final int xStrokeCount;
  private final int yStrokeCount;

  public Frame(int preferredWidth, int preferredHeight, int borderGap, int xStrokeCount, int yStrokeCount) {
    this.preferredWidth = preferredWidth;
    this.preferredHeight = preferredHeight;
    this.borderGap = borderGap;
    this.xStrokeCount = xStrokeCount;
    this.yStrokeCount = yStrokeCount;
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(preferredWidth, preferredHeight);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = configGraphics(g);

    HatchedAxes hatchedAxes = new HatchedAxes(g2, borderGap, 12, getWidth(), getHeight());
    hatchedAxes.draw(xStrokeCount, yStrokeCount);

    Function<Double, Double> function = x -> Math.log1p(1/(Math.pow(x, 2) + 1) - 1) * Math.cos(x);
    Graph graph = new Graph(g2, borderGap, xStrokeCount, yStrokeCount, getWidth(), getHeight(), function);
    graph.draw(10, 10);
  }

  private Graphics2D configGraphics(Graphics graphics) {
    Graphics2D g2 = (Graphics2D)graphics;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setColor(Color.black);
    g2.setStroke(new BasicStroke(1f));
    return g2;
  }

  private static void createAndShowGui() {
    Frame mainPanel = new Frame(800, 800, 30, 20, 20);

    JFrame frame = new JFrame("Frame");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().add(mainPanel);
    frame.pack();
    frame.setLocationByPlatform(true);
    frame.setVisible(true);
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(Frame::createAndShowGui);
  }
}
