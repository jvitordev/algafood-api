package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroCidadeService {

    private static final String MSG_CIDADE_EM_USO 
        = "A cidade com o id %d não pode ser removida, pois está em uso!";

    @Autowired
    CidadeRepository cidadeRepository;

    @Autowired
    EstadoRepository estadoRepository;

    @Autowired
    CadastroEstadoService cadastroEstado;

    @Transactional
    public Cidade salvar(Cidade cidade) {

		Long estadoId = cidade.getEstado().getId();

		Estado estado = cadastroEstado.buscarOuFalhar(estadoId);
		
		cidade.setEstado(estado);

		return cidadeRepository.save(cidade);
    }
    
    @Transactional
    public void excluir(Long id) {
        try {

            cidadeRepository.deleteById(id);
            cidadeRepository.flush();

        } catch (EmptyResultDataAccessException e) {

            throw new CidadeNaoEncontradaException(id);

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(
                MSG_CIDADE_EM_USO, 
                id
            ));
        }
    }

    public Cidade buscarOuFalhar (Long id) {

        return cidadeRepository.findById(id).orElseThrow(
            () -> new CidadeNaoEncontradaException(id)
        );
    }
}
