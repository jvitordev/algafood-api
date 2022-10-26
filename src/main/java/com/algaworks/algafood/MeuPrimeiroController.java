package com.algaworks.algafood;

import javax.validation.constraints.Email;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.algaworks.algafood.di.modelo.Cliente;
import com.algaworks.algafood.di.service.AtivacaoClienteService;

@Controller
public class MeuPrimeiroController {
	
	private AtivacaoClienteService ativacaoClienteService;

	public MeuPrimeiroController(AtivacaoClienteService ativacaoClienteService) {
		this.ativacaoClienteService = ativacaoClienteService;
	}

	// Define a rota
	@GetMapping("/hello")
	// Indica que o retorno do método deve ser escrito na resposta para o cliente
	@ResponseBody
	public String hello() {
		Cliente joao = new Cliente("João", "email@email.com", "88981545847");
		ativacaoClienteService.ativar(joao);
		
		return "Boa noite Luana!";
	}
}
