package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.PermissaoNaoEncontradaException;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.PermissaoRepository;

@Service
public class CadastroPermissaoService {

    private static final String MSG_PERMISSAO_EM_USO 
        = "Não foi possível excluir a permissao de id %d, pois está em uso!";

    @Autowired
    PermissaoRepository permissaoRepository;

    public Permissao salvar(Permissao permissao) {

        return permissaoRepository.save(permissao);
    }

    public void excluir(Long id) {
        try {

            permissaoRepository.deleteById(id);

        } catch (EmptyResultDataAccessException e) {
            
            throw new PermissaoNaoEncontradaException(id);

        } catch (DataIntegrityViolationException e) {

            throw new EntidadeEmUsoException(
                    String.format(MSG_PERMISSAO_EM_USO, id));
        }
    }

    public Permissao buscarOuFalhar (Long id) {
        
        return permissaoRepository.findById(id).orElseThrow(
            () -> new PermissaoNaoEncontradaException(id)
        );
    }
}
