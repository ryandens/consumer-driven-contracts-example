package com.github.ryandens.provider;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ryandens.provider.messages.CoffeeOrder;
import com.github.ryandens.provider.messages.Receipt;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/coffee")
public final class CoffeeService {

  private final PriceService priceService;
  private final ObjectMapper objectMapper;

  public CoffeeService(final PriceService priceService, final ObjectMapper objectMapper) {
    this.priceService = priceService;
    this.objectMapper = objectMapper;
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response makeOrder(final CoffeeOrder coffeeOrder) {
    final double price = priceService.calculate(coffeeOrder);

    final Receipt receipt = Receipt.of(coffeeOrder, price);
    final String body;
    try {
      body = objectMapper.writeValueAsString(receipt);
    } catch (JsonProcessingException e) {
      return Response.serverError().entity("Error serializing response").build();
    }
    return Response.ok().entity(body).build();
  }
}
