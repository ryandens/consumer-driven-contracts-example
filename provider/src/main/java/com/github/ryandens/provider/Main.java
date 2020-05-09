package com.github.ryandens.provider;

import com.sun.net.httpserver.HttpServer;
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
    final HttpServer httpServer = createHttpServer(true, 8080);
    Runtime.getRuntime().addShutdownHook(new Thread(() -> httpServer.stop(0)));
    Thread.currentThread().join();
  }

  static HttpServer createHttpServer(final boolean startHttpServer, final int port) {
    final var services =
        Set.of(new CoffeeService(new PriceService()), new JacksonJaxbJsonProvider());
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
    return JdkHttpServerFactory.createHttpServer(
        UriBuilder.fromUri("http://localhost/").port(port).build(),
        resourceConfig,
        startHttpServer);
  }
}
