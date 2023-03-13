package com.algaworks.algafood.domain.service;

import java.time.OffsetDateTime;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.enumeration.StatusPedido;

@Service
public class FluxoPedidoService {

    private static final String STATUS_NAO_PODE_SER_ALTERADO = "Status do pedido %d não pode ser alterado de %s para %s";

    @Autowired
    private EmissaoPedidoService emissaoPedido;

    @Transactional
    public void confirmar(Long pedidoId) {

        Pedido pedido = emissaoPedido.buscarOuFalhar(pedidoId);

        if (!pedido.getStatus().equals(StatusPedido.CRIADO)) {

            throw new NegocioException(String.format(
                    STATUS_NAO_PODE_SER_ALTERADO,
                    pedido.getId(),
                    pedido.getStatus().getDescricao(),
                    StatusPedido.CONFIRMADO.getDescricao()));
        }

        pedido.setStatus(StatusPedido.CONFIRMADO);
        pedido.setDataConfirmacao(OffsetDateTime.now());
    }

    @Transactional
    public void entregar(Long pedidoId) {

        Pedido pedido = emissaoPedido.buscarOuFalhar(pedidoId);

        if (!pedido.getStatus().equals(StatusPedido.CONFIRMADO)) {

            throw new NegocioException(String.format(
                    STATUS_NAO_PODE_SER_ALTERADO,
                    pedido.getId(),
                    pedido.getStatus().getDescricao(),
                    StatusPedido.ENTREGUE.getDescricao()));
        }

        pedido.setStatus(StatusPedido.ENTREGUE);
        pedido.setDataEntrega(OffsetDateTime.now());
    }

    @Transactional
    public void cancelar(Long pedidoId) {

        Pedido pedido = emissaoPedido.buscarOuFalhar(pedidoId);

        if (!pedido.getStatus().equals(StatusPedido.CRIADO)) {

            throw new NegocioException(String.format(
                    STATUS_NAO_PODE_SER_ALTERADO,
                    pedido.getId(),
                    pedido.getStatus().getDescricao(),
                    StatusPedido.CANCELADO.getDescricao()));
        }

        pedido.setStatus(StatusPedido.CANCELADO);
        pedido.setDataCancelamento(OffsetDateTime.now());
    }

}