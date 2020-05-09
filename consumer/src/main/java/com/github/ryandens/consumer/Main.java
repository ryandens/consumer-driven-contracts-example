package com.github.ryandens.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ryandens.consumer.messages.CoffeeOrder;
import com.github.ryandens.consumer.messages.Receipt;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public final class Main {

  private static final HttpClient httpClient = HttpClient.newHttpClient();
  private static final ObjectMapper objectMapper = new ObjectMapper();

  /**
   * Entry point of the API Consumer application, responsible for starting and stopping resources
   */
  public static void main(final String[] args) {
    final Receipt receipt;
    try {
      receipt =
          sendOrder(
              CoffeeOrder.of(CoffeeOrder.Size.LARGE, CoffeeOrder.Bean.CATURRA),
              "http://ryandens.com");
    } catch (InterruptedException | IOException e) {
      throw new RuntimeException(e);
    }
    System.out.println("receipt: " + receipt.toString());
  }

  public static Receipt sendOrder(final CoffeeOrder coffeeOrder, final String hostName)
      throws InterruptedException, IOException {
    final var request =
        HttpRequest.newBuilder(URI.create(hostName + "/coffee"))
            .header("Content-Type", "application/json")
            .POST(
                HttpRequest.BodyPublishers.ofByteArray(objectMapper.writeValueAsBytes(coffeeOrder)))
            .build();

    return objectMapper.readValue(
        httpClient.send(request, HttpResponse.BodyHandlers.ofString()).body(), Receipt.class);
  }
}
