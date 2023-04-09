package com.algaworks.algafood.api.model;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.algaworks.algafood.api.model.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "restaurantes")
@Setter
@Getter
public class RestauranteModel extends RepresentationModel<RestauranteModel> {

	@JsonView({ RestauranteView.Resumo.class, RestauranteView.ApenasNome.class })
	private Long id;

	@JsonView({ RestauranteView.Resumo.class, RestauranteView.ApenasNome.class })
	private String nome;

	@JsonView(RestauranteView.Resumo.class)
	private BigDecimal taxaFrete;
	
	private CozinhaModel cozinha;
	private Boolean ativo;
	private Boolean aberto;
	private EnderecoModel endereco;
	
}
