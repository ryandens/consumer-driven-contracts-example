package com.github.ryandens.provider;

import com.github.ryandens.provider.messages.CoffeeOrder;
import com.github.ryandens.provider.messages.Receipt;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/coffee")
public final class CoffeeService {

  private final PriceService priceService;

  public CoffeeService(final PriceService priceService) {
    this.priceService = priceService;
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Receipt makeOrder(final CoffeeOrder coffeeOrder) {
    // business logic
    final double price = priceService.calculate(coffeeOrder);

    return Receipt.of(coffeeOrder, price);
  }
}
