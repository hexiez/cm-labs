package com.example.cmlabs.graphics.functions;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.regex.Pattern;

public class HatchedAxes {
  private final Graphics2D g2;
  private final int borderGap;
  private final int strokeSize;
  private final int graphWidth;
  private final int graphHeight;
  private final double xMax;
  private final double yMax;
  private Integer fontSize;

  public HatchedAxes(Graphics2D g2, int borderGap, int strokeSize, int graphWidth, int graphHeight, double xMax, double yMax) {
    this.g2 = g2;
    this.borderGap = borderGap;
    this.strokeSize = strokeSize;
    this.graphWidth = graphWidth;
    this.graphHeight = graphHeight;
    this.xMax = xMax;
    this.yMax = yMax;
  }

  public HatchedAxes(Graphics2D g2, int borderGap, int strokeSize, int graphWidth, int graphHeight, double xMax, double yMax, Integer fontSize) {
    this(g2, borderGap, strokeSize, graphWidth, graphHeight, xMax, yMax);
    this.fontSize = fontSize;
  }

  public void draw(int xCount, int yCount) {
    Font prevFont = g2.getFont();
    g2.setFont(new Font("Serif", Font.PLAIN, fontSize == null ? graphWidth / 60 : fontSize));
    drawXAxisStrokes(xCount);
    drawYAxisStrokes(yCount);
    drawXAxis();
    drawYAxis();
    g2.setFont(prevFont);
  }

  private void drawXAxis() {
    int x0 = borderGap;
    int y0 = (graphHeight - 2 * borderGap) / 2 + borderGap;
    int x1 = graphWidth - borderGap;
    int y1 = y0;
    g2.drawLine(x0, y0, x1, y1);
  }

  private void drawYAxis() {
    int x0 = (graphWidth - 2 * borderGap) / 2 + borderGap;
    int y0 = graphHeight - borderGap;
    int x1 = x0;
    int y1 = borderGap;
    g2.drawLine(x0, y0, x1, y1);
  }

  private void drawXAxisStrokes(int strokeCount) {
    for (int i = 0; i < strokeCount + 1; i++) {
      int x0 = i * (graphWidth - borderGap * 2) / strokeCount + borderGap;
      int y0 = (graphHeight - 2 * borderGap) / 2 + borderGap + strokeSize / 2;
      int x1 = x0;
      int y1 = y0 - strokeSize;
      drawSideAxes(x0, graphHeight - borderGap, x1, borderGap);
      g2.drawLine(x0, y0, x1, y1);
      drawXAxisLabel(x0 - 7, y0 + strokeSize, strokeCount, i);
    }
  }

  private void drawYAxisStrokes(int strokeCount) {
    for (int i = 0; i < strokeCount + 1; i++) {
      int x0 = (graphWidth - 2 * borderGap) / 2 + borderGap - strokeSize / 2;
      int y0 = graphHeight - ((i * (graphHeight - borderGap * 2)) / strokeCount + borderGap);
      int x1 = x0 + strokeSize;
      int y1 = y0;
      drawSideAxes(borderGap, y0, graphWidth - borderGap, y1);
      g2.drawLine(x0, y0, x1, y1);
      drawYAxisLabel(x0 - 3 * strokeSize, y0 + 5, strokeCount, i);
    }
  }

  private void drawXAxisLabel(int x, int y, int strokeCount, int iteration) {
    double result = (-(2 * xMax / strokeCount) * (strokeCount / 2d - iteration));
    String[] doubleParts = String.valueOf(result).split("\\.");

    String label = doubleParts[1].equals("0") ? doubleParts[0] : new DecimalFormat("0E0").format(result);
    g2.drawString(label, x, y);
  }

  private void drawYAxisLabel(int x, int y, int strokeCount, int iteration) {
    double result = (-(2 * yMax / strokeCount) * (strokeCount / 2d - iteration));
    String[] doubleParts = String.valueOf(result).split("\\.");

    String label = doubleParts[1].equals("0") ? doubleParts[0] : new DecimalFormat("0E0").format(result);
    g2.drawString(label, x, y);
  }

  private void drawSideAxes(int x0, int y0, int x1, int y1) {
    g2.setColor(new Color(0xDCDCDC));
    g2.drawLine(x0, y0, x1, y1);
    g2.setColor(Color.BLACK);
  }
}
