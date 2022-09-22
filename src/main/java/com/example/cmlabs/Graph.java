package com.example.cmlabs;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Graph {
  private final Graphics2D g2;
  private final int borderGap;
  private final int xStrokeCount;
  private final int yStrokeCount;
  private final int graphWidth;
  private final int graphHeight;
  private final Function<Double, Double> function;

  private final List<Point> graphPoints = new ArrayList<>();

  public Graph(Graphics2D g2, int borderGap, int xStrokeCount, int yStrokeCount,
               int graphWidth, int graphHeight, Function<Double, Double> function) {
    this.g2 = g2;
    this.borderGap = borderGap;
    this.xStrokeCount = xStrokeCount;
    this.yStrokeCount = yStrokeCount;
    this.graphWidth = graphWidth;
    this.graphHeight = graphHeight;
    this.function = function;
  }

  public void draw(int xMax, int yMax) {
    drawPoints(xMax, yMax);
    drawLines();
  }

  private void drawPoints(int xMax, int yMax) {
    double graphComponentWidth = ((double) graphWidth - 2 * borderGap);
    double graphComponentHeight = ((double) graphHeight - 2 * borderGap);
    double yScale = graphComponentHeight / (2 * yMax);

    for (int i = 0; i < graphComponentWidth; i++) {
      double funcRes = function.apply(((i / graphComponentWidth) - 0.5) * 2 * xMax);
      if (Double.isNaN(funcRes)) continue;
      int x1 = i + borderGap;
      int y1 = (int) ((yMax - funcRes) * yScale + borderGap);
      graphPoints.add(new Point(x1, y1));
    }
  }

  private void drawLines() {
    for (int i = 0; i < graphPoints.size() - 1; i++) {
      int x1 = graphPoints.get(i).x;
      int y1 = graphPoints.get(i).y;
      int x2 = graphPoints.get(i + 1).x;
      int y2 = graphPoints.get(i + 1).y;
      g2.drawLine(x1, y1, x2, y2);
    }
  }
}
