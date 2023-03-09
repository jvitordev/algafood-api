package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.GrupoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.GrupoRepository;

@Service
public class CadastroGrupoService {

    private static final String MSG_GRUPO_EM_USO 
        = "O grupo de id %d não pode ser excluído, pois está em uso";

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private CadastroPermissaoService cadastroPermissao;

    @Transactional
    public Grupo salvar(Grupo grupo){

        return grupoRepository.save(grupo);
    }

    @Transactional
    public void excluir(Long id){
        try {
            grupoRepository.deleteById(id);
            grupoRepository.flush();
            
        } catch (EmptyResultDataAccessException e) {
            throw new GrupoNaoEncontradoException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(
                MSG_GRUPO_EM_USO, 
                id
                ));
        }
    };

    @Transactional
    public void associarPermissao(Long grupoId, Long permissaoId) {

        Grupo grupo = buscarOuFalhar(grupoId);
        Permissao permissao = cadastroPermissao.buscarOuFalhar(permissaoId);

        grupo.associarPermissao(permissao);
    }

    @Transactional
    public void desassociarPermissao(Long grupoId, Long permissaoId) {

        Grupo grupo = buscarOuFalhar(grupoId);
        Permissao permissao = cadastroPermissao.buscarOuFalhar(permissaoId);

        if (!grupo.getPermissoes().contains(permissao)) {
            
            throw new NegocioException(String.format(
                "A permissão de id %s informada não está associada ao grupo de id %d",
                permissaoId,
                grupoId
            ));
        }

        grupo.desassociarPermissao(permissao);
    }

    public Grupo buscarOuFalhar (Long id) {

        return grupoRepository.findById(id).orElseThrow(
            () -> new GrupoNaoEncontradoException(id)
        );
    }
}
