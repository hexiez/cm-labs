package com.example.cmlabs.graphics.functions;

import com.example.cmlabs.graphics.ControlFrame;
import com.example.cmlabs.graphics.Frame;
import com.example.cmlabs.services.DerivativeService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.lang.Math.*;

public class FunctionFrameGenerator {
  private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
  private final int preferredWidth = (int)screenSize.getWidth() / 3;
  private final int preferredHeight = ((int)screenSize.getHeight() - 100) / 2;
  private final int borderGap = 10;
  private final int xStrokeCount = 20;
  private final int yStrokeCount = 20;
  private final int xMax = 20;
  private final int yMax = 25;

  private final Function<Double, Double> function = x -> log(1 / (pow(x, 2) + 1)) * cos(x);
  private final Function<Double, Double> firstAnalyticalDerivative = x -> {
    double a = (pow(x, 2) + 1);
    return (a * sin(x) * log(a) - 2 * x * cos(x))
      / a;
  };
  private final Function<Double, Double> secondAnalyticalDerivative = x -> {
    double a = pow(x, 4) + 2 * pow(x, 2) + 1;
    return (a * cos(x) * log(pow(x, 2) + 1) + (4 * pow(x, 3) + 4 * x) * sin(x) + (2 * pow(x, 2) - 2) * cos(x))
      / a;
  };
  private final Function<Double, Double> thirdAnalyticalDerivative = x -> {
    double a = (pow(x, 6) + 3 * pow(x, 4) + 3 * pow(x, 2) + 1);
    return -( a * sin(x) * log(pow(x, 2) + 1) + (6 * pow(x, 4) - 6) * sin(x) +((-6 * pow(x, 5)) -8 * pow(x, 3) - 18 * x) * cos(x))
      / a;
  };
  private final Function<Double, Double> fourthAnalyticalDerivative = x -> {
    double a = (pow(x, 8) + 4 * pow(x, 6) + 6 * pow(x, 4) + 4 * pow(x, 2) + 1);
    return -(a * cos(x) * log(pow(x, 2) + 1) + (8 * pow(x, 7) + 8 * pow(x, 5) + 56 * pow(x, 3) + 56 * x) * sin(x) + (12 * pow(x, 6) + 60 * pow(x, 2) - 24) * cos(x)) / a;
  };

  private final List<Frame> graphicFrameList = new ArrayList<>();
  private final DerivativeService derivativeService = new DerivativeService();

  public void defaultGenerate() {
    generateFunctionFrame();
    generateFunctionDerivativesFrame();
    generateFunctionAnalyticalDerivativesFrame();
    generateControlPanel();
  }

  private void generateFunctionFrame() {
    Map<String, Function<Double, Double>> functionsMap = new LinkedHashMap<>();
    functionsMap.put("function", function);
    Frame functionPanel = new com.example.cmlabs.graphics.Frame(preferredWidth, preferredHeight, borderGap, xStrokeCount,
      yStrokeCount, xMax, yMax,functionsMap);
    configExitableFrame(functionPanel);
    graphicFrameList.add(functionPanel);
  }

  private void generateFunctionDerivativesFrame() {
    double delta = 2 * (double) xMax / preferredWidth;
    Map<String, Function<Double, Double>> derivativesMap = new LinkedHashMap<>();
    derivativesMap.put("firstDerivative", derivativeService.firstDerivative(function, delta));
    derivativesMap.put("secondDerivative", derivativeService.secondDerivative(function, delta));
    derivativesMap.put("thirdDerivative", derivativeService.thirdDerivative(function, delta));
    derivativesMap.put("fourthDerivative", derivativeService.fourthDerivative(function, delta));
    Frame derivativePanel = new com.example.cmlabs.graphics.Frame(preferredWidth, preferredHeight, borderGap, xStrokeCount,
      yStrokeCount, xMax, yMax,derivativesMap);

    configExitableFrame(derivativePanel);
    graphicFrameList.add(derivativePanel);
  }

  private void generateFunctionAnalyticalDerivativesFrame() {
    Map<String, Function<Double, Double>> analyticalDerivativesMap = new LinkedHashMap<>();
    analyticalDerivativesMap.put("firstAnalyticalDerivative", firstAnalyticalDerivative);
    analyticalDerivativesMap.put("secondAnalyticalDerivative", secondAnalyticalDerivative);
    analyticalDerivativesMap.put("thirdAnalyticalDerivative", thirdAnalyticalDerivative);
    analyticalDerivativesMap.put("fourthAnalyticalDerivative", fourthAnalyticalDerivative);
    Frame analyticalDerivativePanel = new Frame(preferredWidth, preferredHeight, borderGap, xStrokeCount,
      yStrokeCount, xMax, yMax,analyticalDerivativesMap);

    configExitableFrame(analyticalDerivativePanel);
    graphicFrameList.add(analyticalDerivativePanel);
  }

  private void generateControlPanel() {
    JTextField derivativePointField = new JTextField("Derivative x coordinate: ", 15);

    derivativePointField.addFocusListener(new FocusListener() {
      @Override public void focusGained(FocusEvent e) {derivativePointField.setText("");}
      @Override public void focusLost(FocusEvent e) {derivativePointField.setText("X coordinate");}
    });

    derivativePointField.addActionListener(e -> {
      int pointWidth = 7;
      double choseX = 0d;
      for (Frame frame : graphicFrameList) {
        Map<String, List<Point>> nameGraphicMap = frame.getNameGraphicMap();
        for (List<Point> list : nameGraphicMap.values()) {
          choseX = Double.parseDouble(derivativePointField.getText());
          int x = (int) (((choseX) / (2 * xMax) + 0.5) * (preferredWidth - 2 * borderGap) + borderGap) - pointWidth / 2;
          int y = list.stream().filter(point -> point.x == x + pointWidth / 2).limit(1).findFirst().orElseThrow(IllegalArgumentException::new).y - pointWidth / 2;

          Graphics graphics = frame.getGraphics();
          Color prevColor = graphics.getColor();
          graphics.setColor(new Color(0, 0, 0));
          graphics.fillOval(x, y, pointWidth, pointWidth);
          graphics.setColor(prevColor);
        }
      }
      generateFaultsFrame(choseX);
    });

    JFrame jFrame = new ControlFrame(List.of(derivativePointField));
    jFrame.setSize(300, 300);
    jFrame.setVisible(true);
    jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
  }

  private void generateFaultsFrame(double x) {
    Frame faultsPanel1 = new Frame(preferredWidth, preferredHeight, borderGap, xStrokeCount,
      yStrokeCount, 2, 2, Map.of("firstDerivativeFaults", h -> {
        Double apply = firstAnalyticalDerivative.apply(x);
        double v = Math.abs((apply - derivativeService.firstDerivative(function, h).apply(x)) / apply);
        return h >= 0 ? v : Double.NaN;
      })
    );
    Frame faultsPanel2 = new Frame(preferredWidth, preferredHeight, borderGap, xStrokeCount,
      yStrokeCount, 2, 2, Map.of("secondDerivativeFaults", h -> {
        Double apply = secondAnalyticalDerivative.apply(x);
        double v = Math.abs((apply - derivativeService.secondDerivative(function, h).apply(x)) / apply);
        return h >= 0 ? v : Double.NaN;
      })
    );
    Frame faultsPanel3 = new Frame(preferredWidth, preferredHeight, borderGap, xStrokeCount,
      yStrokeCount, 2, 2, Map.of("thirdDerivativeFaults", h -> {
        Double apply = thirdAnalyticalDerivative.apply(x);
        double v = Math.abs((apply - derivativeService.thirdDerivative(function, h).apply(x)) / apply);
        return h >= 0 ? v : Double.NaN;
      })
    );
    Frame faultsPanel4 = new Frame(preferredWidth, preferredHeight, borderGap, xStrokeCount,
      yStrokeCount, 2, 2, Map.of("fourthDerivativeFaults", h -> {
        Double apply = fourthAnalyticalDerivative.apply(x);
        double v = Math.abs((apply - derivativeService.fourthDerivative(function, h).apply(x)) / apply);
        return h >= 0 ? v : Double.NaN;
      })
    );
    configExitableFrame(faultsPanel1);
    configExitableFrame(faultsPanel2);
    configExitableFrame(faultsPanel3);
    configExitableFrame(faultsPanel4);
  }

  private void configFrame(JPanel src) {
    JFrame frame = new JFrame(src.getName());
    frame.getContentPane().add(src);
    frame.pack();
    frame.setLocationByPlatform(true);
    frame.setVisible(true);
    frame.setResizable(false);
  }

  public void configExitableFrame(Frame src) {
    JFrame frame = new JFrame(src.getName());

    JButton incXButton = new JButton();
    incXButton.addActionListener(e -> {
      src.setxMax(src.getxMax() * 2);
      src.revalidate();
      src.repaint();
    });
    incXButton.setMargin(new Insets(5, 0, 5, 0));
    incXButton.setText("x++");

    JButton decXButton = new JButton();
    decXButton.addActionListener(e -> {
      src.setxMax(src.getxMax() / 2);
      src.revalidate();
      src.repaint();
    });
    decXButton.setMargin(new Insets(5, 0, 5, 0));
    decXButton.setText("x--");

    JButton incYButton = new JButton();
    incYButton.addActionListener(e -> {
      src.setyMax(src.getyMax() * 2);
      src.revalidate();
      src.repaint();
    });
    incYButton.setMargin(new Insets(5, 0, 5, 0));
    incYButton.setText("y++");

    JButton decYButton = new JButton();
    decYButton.addActionListener(e -> {
      src.setyMax(src.getyMax() / 2);
      src.revalidate();
      src.repaint();
    });
    decYButton.setMargin(new Insets(5, 0, 5, 0));
    decYButton.setText("y--");

    JPanel contents = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
    contents.add(src);
    JPanel buttonsContent = new JPanel(new GridLayout(2, 2));
    buttonsContent.add(incXButton);
    buttonsContent.add(decXButton);
    buttonsContent.add(incYButton);
    buttonsContent.add(decYButton);
    contents.add(buttonsContent);
    frame.setContentPane(contents);

    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.pack();
    frame.setLocationByPlatform(true);
    frame.setVisible(true);
    frame.setResizable(false);
  }
}
