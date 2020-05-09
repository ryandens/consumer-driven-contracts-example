package com.github.ryandens.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Set;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public final class Main {

  /**
   * Entry point of the API Provider application, responsible for starting and stopping resources
   */
  public static void main(final String[] args) throws InterruptedException {
    final var services =
        Set.of(
            new CoffeeService(new PriceService(), new ObjectMapper()),
            new JacksonJaxbJsonProvider());
    final var resourceConfig =
        ResourceConfig.forApplication(
            new Application() {
              @Override
              public Set<Object> getSingletons() {
                return Set.of(
                    (Feature)
                        context -> {
                          services.forEach(context::register);
                          return true;
                        });
              }
            });
    final var httpServer =
        JdkHttpServerFactory.createHttpServer(
            UriBuilder.fromUri("http://localhost/").port(8080).build(), resourceConfig, true);
    Runtime.getRuntime().addShutdownHook(new Thread(() -> httpServer.stop(0)));
    Thread.currentThread().join();
  }
}
