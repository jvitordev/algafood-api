package com.algaworks.algafood.di.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.di.modelo.Cliente;
import com.algaworks.algafood.di.notificacao.Notificador;

@Component
public class AtivacaoClienteService {

	@Autowired
	private Notificador notificador;
	
	// Usado para definir o método construtor que deverá ser usado pelo Spring
//	@Autowired
//	public AtivacaoClienteService(Notificador notificador) {
//		this.notificador = notificador;
//	}

//	public AtivacaoClienteService(String qualquer) {
//		
//	}
	
	public void activate(Cliente cliente) {
		cliente.activate();
		
		notificador.notificar(cliente, "Seu cadastro no sistema está ativo!");
	}

//	@Autowired
	public void setNotificador(Notificador notificador) {
		this.notificador = notificador;
	}
}
