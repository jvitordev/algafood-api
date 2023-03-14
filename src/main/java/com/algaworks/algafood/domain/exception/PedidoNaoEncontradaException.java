package com.algaworks.algafood.domain.exception;

public class PedidoNaoEncontradaException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public PedidoNaoEncontradaException(String codigoPedido) {
        super(String.format("Não existe um pedido de código %s", codigoPedido));
    }
}
