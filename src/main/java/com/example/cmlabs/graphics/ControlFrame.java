package com.example.cmlabs.graphics;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ControlFrame extends JFrame {

  public ControlFrame(List<Component> components) {
    setSize(300, 100);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER));
    components.forEach(component -> getContentPane().add(component));
    setVisible(true);
  }
}
