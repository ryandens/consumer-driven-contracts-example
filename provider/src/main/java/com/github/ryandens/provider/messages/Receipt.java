package com.github.ryandens.provider.messages;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Receipt {

  public static Receipt of(final CoffeeOrder coffeeOrder, final double price) {
    return new AutoValue_Receipt(coffeeOrder, price);
  }

  public abstract CoffeeOrder coffeeOrder();

  public abstract double price();
}
