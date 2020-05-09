package com.github.ryandens.consumer.messages;

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
    SMALL,
    MEDIUM,
    LARGE
  }

  public enum Bean {
    HAZELNUT,
    CATURRA,
    ROBUSTA;
  }
}
