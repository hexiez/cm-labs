package com.example.cmlabs;

import java.awt.*;

public class HatchedAxes {
  private final Graphics2D g2;
  private final int borderGap;
  private final int strokeSize;
  private final int graphWidth;
  private final int graphHeight;

  public HatchedAxes(Graphics2D g2, int borderGap, int strokeSize, int graphWidth, int graphHeight) {
    this.g2 = g2;
    this.borderGap = borderGap;
    this.strokeSize = strokeSize;
    this.graphWidth = graphWidth;
    this.graphHeight = graphHeight;
  }

  public void draw(int xCount, int yCount) {
    drawXAxis();
    drawYAxis();
    drawXStrokes(xCount);
    drawYStrokes(yCount);
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

  private void drawXStrokes(int strokeCount) {
    for (int i = 0; i < strokeCount + 1; i++) {
      int x0 = i * (graphWidth - borderGap * 2) / strokeCount + borderGap;
      int y0 = (graphHeight - 2 * borderGap) / 2 + borderGap + strokeSize / 2;
      int x1 = x0;
      int y1 = y0 - strokeSize;
      g2.drawLine(x0, y0, x1, y1);
    }
  }

  private void drawYStrokes(int strokeCount) {
    for (int i = 0; i < strokeCount + 1; i++) {
      int x0 = (graphWidth - 2 * borderGap) / 2 + borderGap - strokeSize / 2;
      int y0 = graphHeight - ((i * (graphHeight - borderGap * 2)) / strokeCount + borderGap);
      int x1 = x0 + strokeSize;
      int y1 = y0;
      g2.drawLine(x0, y0, x1, y1);
    }
  }
}
