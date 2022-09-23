package com.example.cmlabs;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Graph {
  private final Graphics2D g2;
  private final int borderGap;
  private final int xMax;
  private final int yMax;
  private final double graphComponentWidth;
  private final double graphComponentHeight;

  private final List<Point> graphPoints = new ArrayList<>();

  public Graph(Graphics2D g2, int borderGap, int xMax, int yMax, int graphWidth, int graphHeight) {
    this.g2 = g2;
    this.borderGap = borderGap;
    this.xMax = xMax;
    this.yMax = yMax;
    this.graphComponentWidth = ((double) graphWidth - 2 * borderGap);;
    this.graphComponentHeight = ((double) graphHeight - 2 * borderGap);
  }

  public void draw(Function<Double, Double> function) {
    drawPoints(function);
    drawLines();

    drawDerivativePoints(function);
    drawLines();
  }

  private void drawPoints(Function<Double, Double> function) {
    for (int i = 0; i < graphComponentWidth; i++) {
      double funcRes = function.apply(((i / graphComponentWidth) - 0.5) * 2 * xMax);
      if (Double.isNaN(funcRes)) continue;
      int x1 = i + borderGap;
      double yScale = graphComponentHeight / (2 * yMax);
      int y1 = (int) ((yMax - funcRes) * yScale + borderGap);
      graphPoints.add(new Point(x1, y1));
    }
  }

  private void drawDerivativePoints(Function<Double, Double> function) {
    Function<Double, Double> derivative = x ->
      (function.apply(x) - function.apply(x - xMax / graphComponentWidth))
      * graphComponentWidth / xMax;
    drawPoints(derivative);
  }

  private void drawLines() {
    for (int i = 0; i < graphPoints.size() - 1; i++) {
      int x1 = graphPoints.get(i).x;
      int y1 = graphPoints.get(i).y;
      int x2 = graphPoints.get(i + 1).x;
      int y2 = graphPoints.get(i + 1).y;
      g2.drawLine(x1, y1, x2, y2);
    }
    graphPoints.clear();
  }
}
