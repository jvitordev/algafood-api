package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;

@Service
public class CadastroFormaPagamentoService {

    @Autowired
    FormaPagamentoRepository formaPagamentoRepository;

    public FormaPagamento salvar(FormaPagamento formaPagamento) {

        return formaPagamentoRepository.save(formaPagamento);
    }

    public void excluir(Long id) {
        try {
            formaPagamentoRepository.findById(id)
                    .orElseThrow(() -> new EntidadeNaoEncontradaException(
                            String.format("Não existe forma de pagamento cadastrada com o id %d", id)));

            formaPagamentoRepository.deleteById(id);

        } catch (DataIntegrityViolationException e) {
            
            throw new EntidadeEmUsoException(
                    String.format("A forma de pagamanto de id %d não pode ser excluída, pois está em uso!", id));
        }
    }
}
