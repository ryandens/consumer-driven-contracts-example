package com.github.ryandens.provider.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.auto.value.AutoValue;

@AutoValue
public abstract class CoffeeOrder {

  @JsonProperty("size")
  public abstract Size size();

  @JsonProperty("bean")
  public abstract Bean bean();

  @JsonCreator
  public static CoffeeOrder of(
      @JsonProperty("size") final Size size, @JsonProperty("bean") final Bean bean) {
    return new AutoValue_CoffeeOrder(size, bean);
  }

  /** Size of a coffee */
  public enum Size {
    SMALL(8),
    MEDIUM(12),
    LARGE(20);

    /** number of ounces of coffee in the size */
    public final int ounces;

    Size(final int ounces) {
      this.ounces = ounces;
    }
  }

  public enum Bean {
    HAZELNUT(0.1),
    CATURRA(0.2),
    ROBUSTA(0.3);

    /** Price per ounce we charge for the coffee beans, in USD */
    public final double pricePerOunce;

    Bean(final double pricePerOunce) {
      this.pricePerOunce = pricePerOunce;
    }
  }
}
