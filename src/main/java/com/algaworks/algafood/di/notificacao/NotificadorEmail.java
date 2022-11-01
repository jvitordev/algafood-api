package com.algaworks.algafood.di.notificacao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import com.algaworks.algafood.di.modelo.Cliente;

@TipoDoNotificador(NivelUrgencia.SEM_URGENCIA)
@Component
public class NotificadorEmail implements Notificador {

	@Value("${exemplo.propriedade.spring}")
	private String atributo;
	
	@Override
	public void notificar(Cliente cliente, String mensagem) {
		System.out.println("A propriedade exemplo.propriedade.spring é igual a " + atributo);
		
		System.out.printf("Notificando %s através do e-mail %s: %s\n", cliente.getName(),
				cliente.getEmail(), mensagem);
	}
}
