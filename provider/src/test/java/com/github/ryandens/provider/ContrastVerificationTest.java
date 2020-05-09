package com.github.ryandens.provider;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

@Provider("CoffeeService")
@PactFolder("../consumer/build/pacts/")
final class ContrastVerificationTest {

  @BeforeAll
  static void beforeAll() {
    final var httpServer = Main.createHttpServer(false, 8080);
    httpServer.start();
  }

  @TestTemplate
  @ExtendWith(PactVerificationInvocationContextProvider.class)
  void pactVerificationTestTemplate(final PactVerificationContext context) {
    context.verifyInteraction();
  }
}
