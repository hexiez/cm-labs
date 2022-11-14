package com.example.cmlabs.services;

import java.util.function.Function;

public class DerivativeService {
  public Function<Double, Double> firstDerivative(Function<Double, Double> function, double delta) {
    return x -> {
      double fPrev = function.apply(x - delta);
      double fNext = function.apply(x + delta);
      return (fNext - fPrev) / (2 * delta);
    };
  }

  public Function<Double, Double> secondDerivative(Function<Double, Double> function, double delta) {
    return x -> {
      double fPrev = function.apply(x - delta);
      double fCur = function.apply(x);
      double fNext = function.apply(x + delta);
      return (fNext - 2 * fCur + fPrev) / Math.pow(delta, 2);
    };
  }

  public Function<Double, Double> thirdDerivative(Function<Double, Double> function, double delta) {
    return x -> {
      double fPrev2 = function.apply(x - 2 * delta);
      double fPrev = function.apply(x - delta);
      double fNext = function.apply(x + delta);
      double fNext2 = function.apply(x + 2 * delta);
      return (fNext2 - 2 * fNext + 2 * fPrev - fPrev2) / (2 * Math.pow(delta, 3));
    };
  }

  public Function<Double, Double> fourthDerivative(Function<Double, Double> function, double delta) {
    return x -> {
      double fPrev2 = function.apply(x - 2 * delta);
      double fPrev = function.apply(x - delta);
      double fCur = function.apply(x);
      double fNext = function.apply(x + delta);
      double fNext2 = function.apply(x + 2 * delta);
      return (fNext2 - 4 * fNext + 6 * fCur - 4 * fPrev + fPrev2) / Math.pow(delta, 4);
    };
  }
}
