package com.github.ryandens.consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.github.ryandens.consumer.messages.CoffeeOrder;
import com.github.ryandens.consumer.messages.Receipt;
import java.io.IOException;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

/** Unit tests for {@link } */
@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "CoffeeService")
final class CoffeeClientPactTest {

  @Pact(consumer = "CoffeeClient")
  RequestResponsePact sendCoffeeOrder(final PactDslWithProvider builder) {
    return builder
        .uponReceiving("Send a Coffee Order")
        .path("/coffee")
        .body("{\"size\": \"LARGE\", \"bean\":\"HAZELNUT\"}")
        .headers(Collections.singletonMap("Content-Type", "application/json"))
        .method("POST")
        .willRespondWith()
        .body(
            new PactDslJsonBody()
                .decimalType("price", 3.50)
                .object("coffeeOrder")
                .stringValue("size", "LARGE")
                .stringValue("bean", "HAZELNUT")
                .closeObject())
        .headers(Collections.singletonMap("Content-Type", "application/json"))
        .status(200)
        .toPact();
  }

  @Test
  @PactTestFor(pactMethod = "sendCoffeeOrder")
  void testSendCoffeeOrder(final MockServer mockServer) {
    final var coffeeOrder = CoffeeOrder.of(CoffeeOrder.Size.LARGE, CoffeeOrder.Bean.HAZELNUT);

    final Receipt receipt;
    try {
      receipt = Main.sendOrder(coffeeOrder, mockServer.getUrl());
    } catch (InterruptedException | IOException e) {
      throw new AssertionError(e);
    }

    assertEquals(3.50, receipt.price());
    assertEquals(coffeeOrder, receipt.coffeeOrder());
  }

  @Pact(consumer = "CoffeeClient")
  RequestResponsePact sendToWrongUri(final PactDslWithProvider builder) {
    return builder
        .uponReceiving("Send a Coffee Order")
        .path("/wrongUri/coffee")
        .body("{\"size\": \"LARGE\", \"bean\":\"HAZELNUT\"}")
        .headers(Collections.singletonMap("Content-Type", "application/json"))
        .method("POST")
        .willRespondWith()
        .status(404)
        .toPact();
  }

  @Test
  @PactTestFor(pactMethod = "sendToWrongUri")
  void testSendWrongUri(final MockServer mockServer) {
    final var coffeeOrder = CoffeeOrder.of(CoffeeOrder.Size.LARGE, CoffeeOrder.Bean.HAZELNUT);

    final var runtimeExceptionAssertion =
        assertThrows(
            RuntimeException.class,
            () -> {
              try {
                Main.sendOrder(coffeeOrder, mockServer.getUrl() + "/wrongUri");
              } catch (InterruptedException | IOException e) {
                throw new AssertionError(e);
              }
            });
    assertEquals("Unexpected response code: 404", runtimeExceptionAssertion.getMessage());
  }
}
