package com.example.cmlabs.graphics.functions;

import com.example.cmlabs.graphics.Graph;

import java.awt.*;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Function;

public class FunctionalGraph extends Graph {
  private final double xMax;
  private final double yMax;

  public FunctionalGraph(Graphics2D g2, int borderGap, double xMax, double yMax, int graphWidth, int graphHeight) {
    super(g2, borderGap, graphWidth, graphHeight);
    this.xMax = xMax;
    this.yMax = yMax;
  }

  public void draw(Function<Double, Double> function, Color color, String functionName) {
    drawPoints(function);
    drawLines(color);
    createNewPartOfGraphic(functionName);
  }

  public void draw(Function<Double, Double> function, String name) {
    draw(function, colors.getNextColor(), name);
  }

  private void drawPoints(Function<Double, Double> function) {
    double leftX, rightX, funcRes;
    for (int i = 0; i < graphComponentWidth - 1; ++i) {
      leftX = ((i / graphComponentWidth) - 0.5) * 2 * xMax;
      rightX = (((i + 1) / graphComponentWidth) - 0.5) * 2 * xMax;

      funcRes = function.apply(leftX);
      if (Double.isNaN(funcRes)) continue;

      addPoint(i + borderGap, (int) ((yMax - funcRes) * graphComponentHeight / (2 * yMax) + borderGap));
      discontinuityPresent(leftX, rightX, function).ifPresent(value -> parts.addFirst(new ArrayList<>()));
    }
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
}
