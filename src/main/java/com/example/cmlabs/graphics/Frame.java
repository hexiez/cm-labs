package com.example.cmlabs.graphics;

import com.example.cmlabs.graphics.functions.FunctionalGraph;
import com.example.cmlabs.graphics.functions.FunctionsLabel;
import com.example.cmlabs.graphics.functions.HatchedAxes;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
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
  private double xMax;
  private double yMax;
  private Map<String, Function<Double, Double>> functionMap;
  private Map<String, List<Point>> nameGraphicMap;


  public Frame(int preferredWidth, int preferredHeight, int borderGap, int xStrokeCount, int yStrokeCount,
               double xMax, double yMax, List<Function<Double, Double>> functions) {
    this(preferredWidth, preferredHeight, borderGap, xStrokeCount, yStrokeCount,  xMax, yMax);
    this.functionMap = functions.stream().collect(Collectors.toMap(Object::toString, function -> function));
  }

  public Frame(int preferredWidth, int preferredHeight, int borderGap, int xStrokeCount, int yStrokeCount, double xMax, double yMax, Map<String, Function<Double, Double>> namedFunctions) {
    super();
    this.preferredWidth = preferredWidth;
    this.preferredHeight = preferredHeight;
    this.borderGap = borderGap;
    this.xStrokeCount = xStrokeCount;
    this.yStrokeCount = yStrokeCount;
    this.xMax = xMax;
    this.yMax = yMax;
    this.functionMap = namedFunctions;
  }

  public Frame(int preferredWidth, int preferredHeight, int borderGap, int xStrokeCount, int yStrokeCount, double xMax, double yMax) {
    super();
    this.preferredWidth = preferredWidth;
    this.preferredHeight = preferredHeight;
    this.borderGap = borderGap;
    this.xStrokeCount = xStrokeCount;
    this.yStrokeCount = yStrokeCount;
    this.xMax = xMax;
    this.yMax = yMax;
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

    FunctionalGraph graph = new FunctionalGraph(g2, borderGap, xMax, yMax, getWidth(), getHeight());
    functionMap.forEach((s, function) -> graph.draw(function, s));
    nameGraphicMap = graph.graphicParts();

    if (!functionMap.isEmpty()) {
      FunctionsLabel functionsLabel = new FunctionsLabel(g2, borderGap);
      functionsLabel.draw(new ArrayList<>(functionMap.keySet()));
    }
  }

  private Graphics2D configGraphics(Graphics graphics) {
    Graphics2D g2 = (Graphics2D)graphics;
    g2.setFont(new Font("", Font.PLAIN, 12));
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setStroke(new BasicStroke(1f));
    return g2;
  }

  public Map<String, List<Point>> getNameGraphicMap() {
    return nameGraphicMap;
  }

  public Map<String, Function<Double, Double>> getFunctionMap() {
    return functionMap;
  }

  public void setxMax(double xMax) {
    this.xMax = xMax;
  }

  public void setyMax(double yMax) {
    this.yMax = yMax;
  }

  public double getxMax() {
    return xMax;
  }

  public double getyMax() {
    return yMax;
  }
}
