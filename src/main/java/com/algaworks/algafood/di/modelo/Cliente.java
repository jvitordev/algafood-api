package com.algaworks.algafood.di.modelo;


public class Cliente {

	private String name;
	private String email;
	private String telefone;
	private boolean ativo = false;
	
	public Cliente(String name, String email, String telefone) {
		this.name = name;
		this.email = email;
		this.telefone = telefone;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}
	public String getTelefone() {
		return telefone;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void ativar() {
		this.ativo = true;
	}
}
