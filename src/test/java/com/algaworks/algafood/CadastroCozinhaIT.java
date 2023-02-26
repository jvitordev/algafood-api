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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

@SpringBootTest
public class CadastroCozinhaIT {

	@Autowired
	CadastroCozinhaService cadastroCozinha;

	@Test
	public void testarCadastroCozinhaComSucesso() {
		//cenario
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome("Chinesa");

		//acao
		novaCozinha = cadastroCozinha.salvar(novaCozinha);

		//validacao
		assertThat(novaCozinha).isNotNull();
		assertThat(novaCozinha.getId()).isNotNull();
	}

	@Test
	public void testarCadastroCozinhaSemNome() {

		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome(null);

		ConstraintViolationException erroEsperado =
		assertThrows(ConstraintViolationException.class, () -> {

			cadastroCozinha.salvar(novaCozinha);
		});

		assertThat(erroEsperado).isNotNull();
	}

	@Test
	public void deveFalhar_QuandoExcluirCozinhaEmUso() {
		
		EntidadeEmUsoException erroEsperado =
		assertThrows(EntidadeEmUsoException.class, () -> {

			cadastroCozinha.excluir(1L);
		});

		assertThat(erroEsperado).isNotNull();
	}

	@Test
	public void deveFalhar_QuandoExcluirCozinhaInexistente() {

		CozinhaNaoEncontradaException erroEsperado =
		assertThrows(CozinhaNaoEncontradaException.class, () -> {

			cadastroCozinha.excluir(10L);
		});

		assertThat(erroEsperado).isNotNull();
	}

}