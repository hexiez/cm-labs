package com.example.cmlabs.graphics;

import com.example.cmlabs.data.ColorsList;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Graph {
  protected final Graphics2D g2;
  protected final int borderGap;
  protected final double graphComponentWidth;
  protected final double graphComponentHeight;
  protected final ArrayDeque<List<Point>> parts = new ArrayDeque<>(List.of(new ArrayList<>()));
  protected final ColorsList colors = new ColorsList();
  protected final Map<String, List<Point>> nameGraphicMap = new HashMap<>();

  public Graph(Graphics2D g2, int borderGap, int graphWidth, int graphHeight) {
    this.g2 = g2;
    this.g2.setClip(borderGap, borderGap, graphWidth - 2 * borderGap, graphHeight - 2 * borderGap);
    this.borderGap = borderGap;
    this.graphComponentWidth = ((double) graphWidth - 2 * borderGap);
    this.graphComponentHeight = ((double) graphHeight - 2 * borderGap);
  }

  public void addPoint(int x, int y) {
    parts.element().add(new Point(x, y));
  }

  public void drawLines(Color color) {
    g2.setColor(color);
    List<Point> graphic = parts.element();
    for (int i = 0; i < graphic.size() - 1; i++) {
      Point first = graphic.get(i);
      Point second = graphic.get(i + 1);
      g2.drawLine(first.x, first.y, second.x, second.y);
    }
  }

  public void createNewPartOfGraphic(String graphicName) {
    nameGraphicMap.put(graphicName, parts.element());
    parts.addFirst(new ArrayList<>());
  }

  public Map<String, List<Point>> graphicParts() {
    return nameGraphicMap;
  }
}
