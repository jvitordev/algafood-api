package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.PermissaoRepository;

@Service
public class CadastroPermissaoService {

    @Autowired
    PermissaoRepository permissaoRepository;

    public Permissao salvar(Permissao permissao) {

        return permissaoRepository.save(permissao);
    }

    public void excluir(Long id) {
        try {
            permissaoRepository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException(
                    String.format("Não existe permissao cadastrada com o id %d", id)));

            permissaoRepository.deleteById(id);

        } catch (DataIntegrityViolationException e) {

            throw new EntidadeEmUsoException(
                    String.format("Não foi possível excluir a permissao de id %d, pois está em uso!", id));
        }
    }
}
