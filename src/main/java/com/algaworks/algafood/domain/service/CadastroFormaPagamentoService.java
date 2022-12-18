package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.FormaPagamentoNaoEncontradoException;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;

@Service
public class CadastroFormaPagamentoService {

    private static final String MSG_FORMA_PAG_EM_USO 
        = "A forma de pagamanto de id %d não pode ser excluída, pois está em uso!";

    @Autowired
    FormaPagamentoRepository formaPagamentoRepository;

    public FormaPagamento salvar(FormaPagamento formaPagamento) {

        return formaPagamentoRepository.save(formaPagamento);
    }

    public void excluir(Long id) {
        try {

            formaPagamentoRepository.deleteById(id);

        } catch (EmptyResultDataAccessException e) {

            throw new FormaPagamentoNaoEncontradoException(id);

        } catch (DataIntegrityViolationException e) {

            throw new EntidadeEmUsoException(
                    String.format(MSG_FORMA_PAG_EM_USO, id));
        }
    }

    public FormaPagamento buscarOuFalhar(Long id) {
        return formaPagamentoRepository.findById(id).orElseThrow(
            () -> new FormaPagamentoNaoEncontradoException(id));
    }
}
