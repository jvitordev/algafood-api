package com.algaworks.algafood.di.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.algaworks.algafood.di.modelo.Cliente;
import com.algaworks.algafood.di.notificacao.Notificador;


public class AtivacaoClienteService {

	private Notificador notificador;
	
	public AtivacaoClienteService(Notificador notificador) {
		this.notificador = notificador;
	}

	public void activate(Cliente cliente) {
		cliente.activate();
		
		notificador.notificar(cliente, "Seu cadastro no sistema está ativo!");
	}
}
