package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.PedidoNaoEncontradaException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.PedidoRepository;

@Service
public class EmissaoPedidoService {

    private static final String MSG_PEDIDO_EM_USO 
        = "Não foi possível excluir o pedido de id %d, pois está em uso!";

    @Autowired
    PedidoRepository pedidoRepository;

    @Transactional
    public Pedido salvar(Pedido pedido) {

        return pedidoRepository.save(pedido);
    }

    @Transactional
    public void excluir(Long id) {
        try {

            pedidoRepository.deleteById(id);
            pedidoRepository.flush();

        } catch (EmptyResultDataAccessException e) {
            
            throw new PedidoNaoEncontradaException(id);

        } catch (DataIntegrityViolationException e) {

            throw new EntidadeEmUsoException(
                    String.format(MSG_PEDIDO_EM_USO, id));
        }
    }

    public Pedido buscarOuFalhar (Long pedidoId) {
        
        return pedidoRepository.findById(pedidoId).orElseThrow(
            () -> new PedidoNaoEncontradaException(pedidoId)
        );
    }
}
