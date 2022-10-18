package com.example.cmlabs;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Function;

public class Graph {
  private final Graphics2D g2;
  private final int borderGap;
  private final double xMax;
  private final double yMax;
  private final double graphComponentWidth;
  private final double graphComponentHeight;
  private final ArrayDeque<List<Point>> graphics = new ArrayDeque<>();
  private final ColorsList colors = new ColorsList();

  public Graph(Graphics2D g2, int borderGap, double xMax, double yMax, int graphWidth, int graphHeight) {
    this.g2 = g2;
    this.g2.setClip(borderGap, borderGap, graphWidth - 2 * borderGap, graphHeight - 2 * borderGap);
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
    drawLines(colors.getNextColor());
  }

  private void drawPoints(Function<Double, Double> function) {
    graphics.addFirst(new ArrayList<>());
    for (int i = 0; i < graphComponentWidth - 1; ++i) {
      double leftX = ((i / graphComponentWidth) - 0.5) * 2 * xMax;
      double rightX = (((i + 1) / graphComponentWidth) - 0.5) * 2 * xMax;

      double funcRes = function.apply(leftX);
      if (Double.isNaN(funcRes)) continue;

      addPoint(i, funcRes, graphics.element());
      discontinuityPresent(leftX, rightX, function).ifPresent(value -> graphics.addFirst(new ArrayList<>()));
    }
  }

  private void addPoint(double x, double y, List<Point> graphic) {
    int x1 = (int) (x + borderGap);
    int y1 = (int) ((yMax - y) * graphComponentHeight / (2 * yMax) + borderGap);
    graphic.add(new Point(x1, y1));
  }

  private Optional<Double> discontinuityPresent(double leftX, double rightX, Function<Double, Double> function) {
    while(true) {
      double leftY = function.apply(leftX);
      double rightY = function.apply(rightX);
      double middleX = (leftX + rightX) / 2;
      double middleY = function.apply(middleX);
      double leftSlope = Math.abs(slope(leftX, leftY, middleX, middleY));
      double rightSlope = Math.abs(slope(middleX, middleY, rightX, rightY));

      if(Double.isInfinite(leftSlope) || Double.isInfinite(rightSlope)) {
        return Optional.of(middleX);
      } else if (middleX == leftX || middleX == rightX) {
        return Optional.empty();
      }

      if (leftSlope > rightSlope) {
        rightX = middleX;
      } else {
        leftX = middleX;
      }
    }
  }

  private double slope(double x1, double y1, double x2, double y2) {
    return (y2 - y1) / (x2 - x1);
  }

  private void drawLines(Color color) {
    g2.setColor(color);
    for (List<Point> graphic : graphics) {
      for (int i = 0; i < graphic.size() - 1; i++) {
        Point first = graphic.get(i);
        Point second = graphic.get(i + 1);
        g2.drawLine(first.x, first.y, second.x, second.y);
      }
      graphic.clear();
    }
  }
}
