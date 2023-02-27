//package com.algaworks.algafood;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//class AlgafoodApiApplicationTests {
//
//	@Test
//	void contextLoads() {
//	}
//
//}

package com.algaworks.algafood;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CadastroCozinhaIT {

	@LocalServerPort
	private int port;

	@Test
	public void deveRetornarStatus200_QuandoConsultarCozinhas() {

		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		
		given()
			.basePath("/cozinhas")
			.port(port)
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("", hasSize(4))
			.body("nome", hasItems("Indiana", "Tailandesa"));
	}
}