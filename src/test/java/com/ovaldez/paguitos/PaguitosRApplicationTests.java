package com.ovaldez.paguitos;

import com.ovaldez.paguitos.controller.ClientController;
import com.ovaldez.paguitos.dto.Cliente;
import com.ovaldez.paguitos.service.ClienteService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@WebFluxTest(ClientController.class)
@ContextConfiguration(classes = PaguitosRApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PaguitosRApplicationTests {

	@Autowired
	private WebTestClient webTestClient;
	@MockBean
	private ClienteService clienteService;

	private Cliente cliente1;
	private Cliente cliente2;

	@BeforeAll
	public void  init(){
		cliente1 = new Cliente("1","Juan Perez Gomez","8523697412","correo@correo.com","Av de las Partidas 24",true);
		cliente2 = new Cliente("1","Oscar Diaz Juarez","9874456321","email@correo.com","Av de las Partidas 34",true);
	}

	@Test
	void addClientTest() {
		Mono<Cliente> clienteMono = Mono.just(new Cliente("1","Juan Perez Gomez","8523697412","correo@correo.com","Av de las Partidas 24",true));
		when(clienteService.saveCliente(cliente1)).thenReturn(clienteMono);
		webTestClient.post()
				.uri("/api/cliente")
				.body(Mono.just(clienteMono),Cliente.class)
				.exchange()
				.expectStatus()
				.isOk();
	}

	@Test
	void getAllClientesTest(){
		Flux<Cliente> clienteFlux = Flux.just(cliente1,cliente2);
		when(clienteService.getAllClientes()).thenReturn(clienteFlux);
		Flux<Cliente> responseBody = webTestClient.get()
				.uri("/api/cliente")
				.exchange()
				.expectStatus()
				.isOk()
				.returnResult(Cliente.class)
				.getResponseBody();
		StepVerifier.create(responseBody)
				.expectSubscription()
				.expectNext(cliente1)
				.expectNext(cliente2)
				.verifyComplete();
	}

	@Test
	void updateClienteTest(){
		Mono<Cliente> clienteMono = Mono.just(cliente1);
		when(clienteService.updateCliente("123",cliente1)).thenReturn(clienteMono);
		webTestClient.put()
				.uri("/api/cliente/123")
				.body(Mono.just(cliente1),Cliente.class)
				.exchange()
				.expectStatus()
				.isOk();
	}


	@Test
	void deleteClienteTest(){
		given(clienteService.deleteCliente(any())).willReturn(Mono.empty());
	}

}
