package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {
    
    private static final String MSG_RESTAURANTE_EM_USO 
        = "O restaurante de id %d não pode ser excluído, pois está em uso!";

    @Autowired
    RestauranteRepository restauranteRepository;

    @Autowired
    CozinhaRepository cozinhaRepository;

    @Autowired
    CadastroCozinhaService cadastroCozinha;

    @Autowired
    private CadastroCidadeService cadastroCidadeService;

    @Autowired
    CadastroFormaPagamentoService cadastroFormaPagamento;

    @Autowired
    CadastroUsuarioService cadastroUsuario;

    @Transactional
	public Restaurante salvar(Restaurante restaurante) {

		Long cozinhaId = restaurante.getCozinha().getId();
        Long cidadeId = restaurante.getEndereco().getCidade().getId();

		Cozinha cozinha = cadastroCozinha.buscarOuFalhar(cozinhaId);
        Cidade cidade = cadastroCidadeService.buscarOuFalhar(cidadeId);
        
		restaurante.setCozinha(cozinha);
        restaurante.getEndereco().setCidade(cidade);

		return restauranteRepository.save(restaurante);
	}

    @Transactional
    public void excluir(Long id) {
        try {
            restauranteRepository.deleteById(id);
            restauranteRepository.flush();
            
        } catch (EmptyResultDataAccessException e) {

            throw new RestauranteNaoEncontradoException(id);

        } catch (DataIntegrityViolationException e) {

            throw new EntidadeEmUsoException(String.format(
                MSG_RESTAURANTE_EM_USO, 
                id
            ));
        }
        
    }

    public Restaurante buscarOuFalhar (Long id) {

        return restauranteRepository.findById(id).orElseThrow(
            () -> new RestauranteNaoEncontradoException(id));
    }
    
    @Transactional
    public void associarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(formaPagamentoId);
        
        restaurante.adicionarFormaPagamento(formaPagamento);
    }

    @Transactional
	public void desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(formaPagamentoId);
		
		restaurante.removerFormaPagamento(formaPagamento);
	}
    
    @Transactional
    public void associarResponsavel(Long restauranteId, Long usuarioId) {
        
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        Usuario usuario = cadastroUsuario.buscarOuFalhar(usuarioId);
        
        restaurante.adicionarResponsavel(usuario);
    }

    @Transactional
	public void desassociarResponsavel(Long restauranteId, Long usuarioId) {

		Restaurante restaurante = buscarOuFalhar(restauranteId);
		Usuario usuario = cadastroUsuario.buscarOuFalhar(usuarioId);
		
		restaurante.removerResponsavel(usuario);
	}

    @Transactional
    public void ativar(Long restauranteId) {

        Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
        restauranteAtual.ativar();
    }

    @Transactional
    public void inativar(Long restauranteId) {

        Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
        restauranteAtual.inativar();
    }

    @Transactional
	public void ativar(List<Long> restauranteIds) {
		restauranteIds.forEach(this::ativar);
	}
	
	@Transactional
	public void inativar(List<Long> restauranteIds) {
		restauranteIds.forEach(this::inativar);
	}

    @Transactional
    public void abrir(Long restauranteId) {

        Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
        restauranteAtual.abrir();
    }

    @Transactional
    public void fechar(Long restauranteId) {

        Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
        restauranteAtual.fechar();
    }
}
