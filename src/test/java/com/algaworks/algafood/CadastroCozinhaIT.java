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
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.util.DatabaseCleaner;
import com.algaworks.algafood.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroCozinhaIT {

	@LocalServerPort
	private int port;

	private int quantidadeCozinhasCadastradas;
	private String jsonCorretoCozinhaChinesa;
	private int idInexistente;

	@Autowired
	CozinhaRepository cozinhaRepository;
	
	@Autowired
	DatabaseCleaner databaseCleaner;
	
	List<Cozinha> cozinhas = new ArrayList<Cozinha>();


	@BeforeEach
	public void setUp() {

		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cozinhas";

		databaseCleaner.clearTables();
		prepararDados();

		jsonCorretoCozinhaChinesa = ResourceUtils.getContentFromResource(
		"/json/correto/cozinha-chinesa.json");
	}

	@Test
	public void deveRetornarStatus200_QuandoConsultarCozinhas() {

		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}

	@Test
	public void deveRetornarQuantidadeCorretaDeCozinhas_QuandoConsultarCozinhas() {

		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("", hasSize(quantidadeCozinhasCadastradas));
	}

	@Test
	public void deveRetornarStatus201_QuandoCadastrarCozinha() {

		given()
			.body(jsonCorretoCozinhaChinesa)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}

	@Test
	public void deveRetornarRespostaEStatusCorretos_QuandoConsultarCozinhaExistente() {

		Cozinha cozinha = cozinhas.get(new Random().nextInt(cozinhas.size()));

		given()
			.pathParam("cozinhaId", cozinha.getId())
			.accept(ContentType.JSON)
		.when()
			.get("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("nome", equalTo(cozinha.getNome()));
	}
	
	@Test
	public void deveRetornarStatus404_QuandoConsultarCozinhaInexistente() {
		given()
			.pathParam("cozinhaId", idInexistente)
			.accept(ContentType.JSON)
		.when()
			.get("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}

	private void prepararDados() {

		Integer qtdCozinhas = 2;

		for(int i = 1; i <= qtdCozinhas; i++) {

			String iToString = Integer.toString(i);

			Cozinha cozinha = new Cozinha();
			cozinha.setNome("Cozinha " + iToString);
			cozinha = cozinhaRepository.save(cozinha);
			cozinhas.add(cozinha);
		}

		quantidadeCozinhasCadastradas = (int) cozinhaRepository.count();
		idInexistente = quantidadeCozinhasCadastradas + 1;
	}
}