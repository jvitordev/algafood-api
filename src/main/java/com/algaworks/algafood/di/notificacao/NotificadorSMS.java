package com.algaworks.algafood.di.notificacao;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.di.modelo.Cliente;

@Qualifier("normal")
@Component
public class NotificadorSMS implements Notificador {

	@Override
	public void notificar(Cliente cliente, String mensagem) {
		System.out.printf("Notificando %s através do SMS %s: %s\n", cliente.getName(),
				cliente.getTelefone(), mensagem);
	}
}
