package com.github.ryandens.provider;

import com.github.ryandens.provider.messages.CoffeeOrder;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public final class PriceService {

  private final DecimalFormat decimalFormat;

  public PriceService() {
    decimalFormat = new DecimalFormat("#.##");
    decimalFormat.setRoundingMode(RoundingMode.CEILING);
  }

  public double calculate(final CoffeeOrder coffeeOrder) {
    return Double.parseDouble(
        decimalFormat.format(coffeeOrder.bean().pricePerOunce * coffeeOrder.size().ounces));
  }
}
