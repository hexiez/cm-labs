package com.example.cmlabs;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Graph {
  private final Graphics2D g2;
  private final int borderGap;
  private final double xMax;
  private final double yMax;
  private final double graphComponentWidth;
  private final double graphComponentHeight;
  private final List<Point> graphPoints = new ArrayList<>();
  private ArrayDeque<Color> colorQueue = new ArrayDeque<>();

  public Graph(Graphics2D g2, int borderGap, double xMax, double yMax, int graphWidth, int graphHeight) {
    this.g2 = g2;
    this.borderGap = borderGap;
    this.xMax = xMax;
    this.yMax = yMax;
    this.graphComponentWidth = ((double) graphWidth - 2 * borderGap);;
    this.graphComponentHeight = ((double) graphHeight - 2 * borderGap);
  }

  public void draw(Function<Double, Double> function, Color color) {
    drawPoints(function);
    drawLines(color);
  }

  public void draw(Function<Double, Double> function) {
    drawPoints(function);
    if (colorQueue.isEmpty()) colorQueue = new ArrayDeque<>(List.of(
      Color.RED, Color.BLUE, Color.GREEN, Color.MAGENTA));
    drawLines(colorQueue.poll());
  }

  private void drawPoints(Function<Double, Double> function) {
    boolean funcIsAbroadPositive = false;
    boolean funcIsAbroadNegative = false;
    for (int i = 0; i < graphComponentWidth; i++) {
      double funcRes = function.apply(((i / graphComponentWidth) - 0.5) * 2 * xMax);

      if (funcRes > yMax && !funcIsAbroadPositive) {
        funcRes = yMax;
        funcIsAbroadPositive = true;
        addPoint(i, funcRes);
      } else if (funcRes <= yMax && funcIsAbroadPositive) {
        funcRes = yMax;
        funcIsAbroadPositive = false;
        addPoint(i, funcRes);
      } else if (funcRes < -yMax && !funcIsAbroadNegative) {
        funcRes = -yMax;
        funcIsAbroadNegative = true;
        addPoint(i, funcRes);
      } else if (funcRes >= -yMax && funcIsAbroadNegative) {
        funcRes = -yMax;
        funcIsAbroadNegative = false;
        addPoint(i, funcRes);
      } else if (!funcIsAbroadNegative && !funcIsAbroadPositive && !Double.isNaN(funcRes)) {
        addPoint(i, funcRes);
      }
    }
  }

  private void addPoint(double x, double y) {
    int x1 = (int) (x + borderGap);
    int y1 = (int) ((yMax - y) * graphComponentHeight / (2 * yMax) + borderGap);
    graphPoints.add(new Point(x1, y1));
  }

  private void drawDerivativePoints(Function<Double, Double> function) {
    Function<Double, Double> derivative = x ->
      (function.apply(x) - function.apply(x - xMax / graphComponentWidth))
      * graphComponentWidth / xMax;
    drawPoints(derivative);
  }

  private void drawLines(Color color) {
    g2.setColor(color);
    for (int i = 0; i < graphPoints.size() - 1; i++) {
      int x1 = graphPoints.get(i).x;
      int y1 = graphPoints.get(i).y;
      int x2 = graphPoints.get(i + 1).x;
      int y2 = graphPoints.get(i + 1).y;
      if ((y1 != graphComponentHeight + 30 && y1 != borderGap)
        || (y2 != graphComponentHeight + 30  && y2 != borderGap)) {
        g2.drawLine(x1, y1, x2, y2);
      }
    }
    graphPoints.clear();
  }
}
