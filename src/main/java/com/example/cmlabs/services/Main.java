package com.example.cmlabs.services;

import com.example.cmlabs.graphics.ControlFrame;
import com.example.cmlabs.graphics.Frame;
import com.example.cmlabs.graphics.functions.FunctionFrameGenerator;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

public class Main {

  public static void main(String[] args) {
    FunctionFrameGenerator generator = new FunctionFrameGenerator();
    generator.defaultGenerate();
  }
}
