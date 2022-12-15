package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroCidadeService {

    private static final String MSG_CIDADE_EM_USO 
        = "A cidade com o id %d não pode ser removida, pois está em uso!";

    private static final String MSG_CIDADE_NAO_ENCONTRADA 
        = "Não existe cidade cadastrada com o id %d!";

    @Autowired
    CidadeRepository cidadeRepository;

    @Autowired
    EstadoRepository estadoRepository;

    CadastroEstadoService cadastroEstado;

    public Cidade salvar(Cidade cidade) {

		Long estadoId = cidade.getEstado().getId();

		Estado estado = cadastroEstado.buscarOuFalhar(estadoId);
		
		cidade.setEstado(estado);

		return cidadeRepository.save(cidade);
    }
    
    public void excluir(Long id) {
        try {

            cidadeRepository.deleteById(id);

        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(String.format(
            MSG_CIDADE_NAO_ENCONTRADA, 
                id
                ));
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(
                MSG_CIDADE_EM_USO, 
                id
                ));
        }
    }

    public Cidade buscarOuFalhar (Long id) {

        return cidadeRepository.findById(id).orElseThrow(
            () -> new EntidadeNaoEncontradaException(
                String.format(MSG_CIDADE_NAO_ENCONTRADA, id)
            ));
    }
}
