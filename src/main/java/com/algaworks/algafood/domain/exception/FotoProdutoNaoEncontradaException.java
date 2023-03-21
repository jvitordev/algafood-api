package com.algaworks.algafood.domain.exception;

public class FotoProdutoNaoEncontradaException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public FotoProdutoNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public FotoProdutoNaoEncontradaException(Long produtoId, Long restauranteId) {
        this(String.format("Não existe um cadastro de foto de produto com o id %d para o restaurante de id %d",
        produtoId, restauranteId));
    }
}
