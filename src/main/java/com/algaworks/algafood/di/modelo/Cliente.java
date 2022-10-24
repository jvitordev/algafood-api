package com.algaworks.algafood.di.modelo;


public class Cliente {

	private String name;
	private String email;
	private String telefone;
	private boolean active = false;
	
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

	public boolean isActive() {
		return active;
	}

	public void activate() {
		this.active = true;
	}
}
