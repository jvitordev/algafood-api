package com.algaworks.algafood.di.notificacao;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import com.algaworks.algafood.di.modelo.Cliente;

@Profile("desenvolvimento")
@TipoDoNotificador(NivelUrgencia.SEM_URGENCIA)
@Component
public class NotificadorEmailMock implements Notificador {

	public NotificadorEmailMock() {
		System.out.println("Notificador E-mail MOCK");
	}
	
	@Override
	public void notificar(Cliente cliente, String mensagem) {
		System.out.printf("Mock: notificação seria enviada pra %s através do e-mail %s: %s\n", cliente.getName(),
				cliente.getEmail(), mensagem);
	}
}
