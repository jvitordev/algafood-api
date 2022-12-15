package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {
    
    private static final String MSG_RESTAURANTE_EM_USO 
        = "O restaurante de id %d não pode ser excluído, pois está em uso!";

    private static final String MSG_RESTAURANTE_NAO_EXISTE 
        = "Não existe um restaurante cadastrado com o id %d!";

    @Autowired
    RestauranteRepository restauranteRepository;

    @Autowired
    CozinhaRepository cozinhaRepository;

    @Autowired
    CadastroCozinhaService cadastroCozinha;

	public Restaurante salvar(Restaurante restaurante) {

		Long cozinhaId = restaurante.getCozinha().getId();

		Cozinha cozinha = cadastroCozinha.buscarOuFalhar(cozinhaId);
        
		restaurante.setCozinha(cozinha);

		return restauranteRepository.save(restaurante);
	}

    public void excluir(Long id) {
        try {
            restauranteRepository.deleteById(id);
            
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(String.format(
                MSG_RESTAURANTE_NAO_EXISTE, 
                id
                ));
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(
                MSG_RESTAURANTE_EM_USO, 
                id
                ));
        }
        
    }

    public Restaurante buscarOuFalhar (Long id) {

        return restauranteRepository.findById(id).orElseThrow(
            () -> new EntidadeNaoEncontradaException(
                String.format(MSG_RESTAURANTE_NAO_EXISTE, id)
            )
        );
    }
}
