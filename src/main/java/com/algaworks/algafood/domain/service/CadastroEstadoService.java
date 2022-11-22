package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    public Estado salvar(Estado estado){

        return estadoRepository.save(estado);
    }

    public void excluir(Long id){
        try {
            estadoRepository.deleteById(id);
            
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(String.format(
                "Não existe cadastro de estado com o id %d", 
                id
                ));
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(
                "O estado de id %d não pode ser excluído, pois está em uso", 
                id
                ));
        }
    };
}
