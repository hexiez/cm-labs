package com.example.cmlabs.graphics.functions;

import com.example.cmlabs.data.ColorsList;

import java.awt.*;
import java.util.List;

public class FunctionsLabel {
  private final ColorsList colors = new ColorsList();
  private final Graphics2D g2;
  private int x;
  private int y;

  public FunctionsLabel(Graphics2D g2, int borderGap) {
    this.g2 = g2;
    this.x = borderGap + 10;
    this.y = borderGap + 10;
  }

  public void draw(List<String> functionNames) {
    functionNames.forEach(name -> {
      g2.setColor(colors.getNextColor());
      g2.drawString(name, x, y += 13);
    });
  }
}
