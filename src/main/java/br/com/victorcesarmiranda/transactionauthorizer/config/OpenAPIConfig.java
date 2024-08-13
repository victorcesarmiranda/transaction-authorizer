package br.com.victorcesarmiranda.transactionauthorizer.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenAPIConfig {

  @Bean
  public OpenAPI transactionAthorizerApi() {
    var server = new Server();
    server.setUrl("http://localhost:8080");
    server.setDescription("URL em que a aplicação está sendo executada");

    Contact contact = new Contact();
    contact.setEmail("victorcesarmiranda@gmail.com");
    contact.setName("Victor Cesar Miranda");
    contact.setUrl("https://github.com/victorcesarmiranda");

    Info info = new Info()
        .title("API de Autorização de Transações")
        .version("1.0")
        .contact(contact)
        .description("API para autorizar e processar transações de cartão de crédito de benefícios");

    return new OpenAPI().info(info).servers(List.of(server));
  }
}