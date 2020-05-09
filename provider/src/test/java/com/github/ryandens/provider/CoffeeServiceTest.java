package com.github.ryandens.provider;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.ryandens.provider.messages.CoffeeOrder;
import org.junit.jupiter.api.Test;

/** Unit tests for {@link CoffeeService} */
final class CoffeeServiceTest {

  /** test business logic with unit or integartion tests! */
  @Test
  void orderCoffee() {
    final var coffeeService = new CoffeeService(new PriceService());
    final var order = CoffeeOrder.of(CoffeeOrder.Size.MEDIUM, CoffeeOrder.Bean.HAZELNUT);
    final var receipt = coffeeService.makeOrder(order);
    assertEquals(order, receipt.coffeeOrder());
    assertEquals(1.21, receipt.price());
  }
}
