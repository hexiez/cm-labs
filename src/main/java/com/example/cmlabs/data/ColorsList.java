package com.example.cmlabs.data;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public final class ColorsList {
  private final List<Color> colors = new ArrayList<>();
  private int index = 0;

  public ColorsList() {
    colors.addAll(List.of(
      new Color(0xff0000),
      new Color(0x0000ff),
      new Color(0x008000),
      new Color(0x8b00ff),
      new Color(0x000000)
    ));
  }

  public void addColors(List<Color> colors) {
    this.colors.addAll(colors);
  }

  public Color getNextColor() {
    if (colors.isEmpty()) return null;
    if (index == colors.size()) index = 0;
    return colors.get(index++);
  }

  public boolean isEmpty() {
    return colors.isEmpty();
  }
}
