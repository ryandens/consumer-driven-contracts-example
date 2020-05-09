package com.github.ryandens.consumer.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Receipt {

  @JsonCreator
  public static Receipt of(
      @JsonProperty("coffeeOrder") final CoffeeOrder coffeeOrder,
      @JsonProperty("price") final double price) {
    return new AutoValue_Receipt(coffeeOrder, price);
  }

  @JsonProperty("coffeeOrder")
  public abstract CoffeeOrder coffeeOrder();

  @JsonProperty("price")
  public abstract double price();
}
