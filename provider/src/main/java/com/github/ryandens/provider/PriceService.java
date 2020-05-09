package com.github.ryandens.provider;

import com.github.ryandens.provider.messages.CoffeeOrder;

public final class PriceService {

  public double calculate(final CoffeeOrder coffeeOrder) {
    return coffeeOrder.bean().pricePerOunce * coffeeOrder.size().ounces;
  }
}
