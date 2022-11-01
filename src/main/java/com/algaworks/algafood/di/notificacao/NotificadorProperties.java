package com.algaworks.algafood.di.notificacao;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("notificador.email")
public class NotificadorProperties {

	/**
	 * Host Servidor
	 */
	private String hostServidor;
	
	/**
	 * Host Porta
	 */
	private Integer hostPorta;
	
	public String getHostServidor() {
		return hostServidor;
	}
	public void setHostServidor(String hostServidor) {
		this.hostServidor = hostServidor;
	}
	public Integer getHostPorta() {
		return hostPorta;
	}
	public void setHostPorta(Integer hostPorta) {
		this.hostPorta = hostPorta;
	}
}
