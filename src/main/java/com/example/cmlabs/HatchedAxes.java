package com.example.cmlabs;

import javax.swing.*;
import java.awt.*;

public class HatchedAxes {
  private final Graphics2D g2;
  private final int borderGap;
  private final int strokeSize;
  private final int graphWidth;
  private final int graphHeight;
  private final double xMax;
  private final double yMax;

  public HatchedAxes(Graphics2D g2, int borderGap, int strokeSize, int graphWidth, int graphHeight, double xMax, double yMax) {
    this.g2 = g2;
    this.borderGap = borderGap;
    this.strokeSize = strokeSize;
    this.graphWidth = graphWidth;
    this.graphHeight = graphHeight;
    this.xMax = xMax;
    this.yMax = yMax;
  }

  public void draw(int xCount, int yCount) {
    drawXAxisStrokes(xCount);
    drawYAxisStrokes(yCount);
    drawXAxis();
    drawYAxis();
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

  private void drawXAxisLabel(int x, int y, int strokeCount, int iteration) {
    int result = (int) (-(2 * xMax / strokeCount) * (strokeCount / 2d - iteration));
    g2.drawString(String.valueOf(result), x, y);
  }

  private void drawYAxisStrokes(int strokeCount) {
    for (int i = 0; i < strokeCount + 1; i++) {
      int x0 = (graphWidth - 2 * borderGap) / 2 + borderGap - strokeSize / 2;
      int y0 = graphHeight - ((i * (graphHeight - borderGap * 2)) / strokeCount + borderGap);
      int x1 = x0 + strokeSize;
      int y1 = y0;
      drawSideAxes(borderGap, y0, graphWidth - borderGap, y1);
      g2.drawLine(x0, y0, x1, y1);
      drawYAxisLabel(x0 - 2 * strokeSize, y0 + 7, strokeCount, i);
    }
  }

  private void drawYAxisLabel(int x, int y, int strokeCount, int iteration) {
    int result = (int) (-(2 * yMax / strokeCount) * (strokeCount / 2d - iteration));
    if (result == 0) return;
    g2.drawString(String.valueOf(result), x, y);
  }

  private void drawSideAxes(int x0, int y0, int x1, int y1) {
    g2.setColor(Color.LIGHT_GRAY);
    g2.drawLine(x0, y0, x1, y1);
    g2.setColor(Color.BLACK);
  }
}
