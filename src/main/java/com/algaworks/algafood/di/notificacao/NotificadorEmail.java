package com.algaworks.algafood.di.notificacao;

import com.algaworks.algafood.di.modelo.Cliente;

public class NotificadorEmail implements Notificador {

	private boolean caixaAlta;
	private String hostServidor;

	public NotificadorEmail(String hostServidor) {
		this.hostServidor = hostServidor;
	}

	@Override
	public void notificar(Cliente cliente, String mensagem) {
		if (this.caixaAlta) {
			mensagem = mensagem.toUpperCase();
		}

		System.out.printf("Notificando %s através do e-mail %s usando SMTP %s: %s\n", cliente.getName(),
				cliente.getEmail(), this.hostServidor, mensagem);
	}

	public void setCaixaAlta(boolean caixaAlta) {
		this.caixaAlta = caixaAlta;
	}
}
