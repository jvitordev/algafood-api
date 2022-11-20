package com.algaworks.algafood.domain.repository;

import java.util.List;

import com.algaworks.algafood.domain.model.Estado;

public interface EstadoRepository {

	public List<Estado> todos();
	public Estado porId(Long id);
	public Estado adicionar(Estado estado);
	public void remover(Long id);
}
