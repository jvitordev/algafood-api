package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembier.RestauranteInputDisassembier;
import com.algaworks.algafood.api.assembier.RestauranteModelAssembier;
import com.algaworks.algafood.api.model.RestauranteModel;
import com.algaworks.algafood.api.model.input.RestauranteInput;
import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @Autowired
    RestauranteModelAssembier restauranteModelAssembier;

    @Autowired
    RestauranteInputDisassembier restauranteInputDisassembier;

    @GetMapping
    public List<RestauranteModel> todos() {

        return restauranteModelAssembier.toCollectionModel(restauranteRepository.findAll());
    }

    @GetMapping("/{id}")
    public RestauranteModel buscar(@PathVariable Long id) {
            
        return restauranteModelAssembier.toModel(cadastroRestaurante.buscarOuFalhar(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteModel adicionar(
        @RequestBody @Valid RestauranteInput restauranteInput) {

        try {

            Restaurante restaurante = restauranteInputDisassembier.toDomainObject(restauranteInput);
            
            return restauranteModelAssembier.toModel(cadastroRestaurante.salvar(restaurante));

        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {

            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{id}")
    public RestauranteModel atualizar(
        @PathVariable Long id,
        @Valid @RequestBody RestauranteInput restauranteInput) {

        Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(id);

        restauranteInputDisassembier.copyToDomainModel(restauranteInput, restauranteAtual);

        try {

            return restauranteModelAssembier.toModel(cadastroRestaurante.salvar(restauranteAtual));

        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{restauranteId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativar(@PathVariable Long restauranteId) {

        cadastroRestaurante.ativar(restauranteId);
    }

    @DeleteMapping("/{restauranteId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativar(@PathVariable Long restauranteId) {

        cadastroRestaurante.inativar(restauranteId);
    }

    @PutMapping("/{restauranteId}/abertura")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void abertura(@PathVariable Long restauranteId) {

        cadastroRestaurante.abrir(restauranteId);
    }

    @PutMapping("/{restauranteId}/fechamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void fechamento(@PathVariable Long restauranteId) {

        cadastroRestaurante.fechar(restauranteId);
    }

    @PutMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativarMultiplos(@RequestBody List<Long> restauranteIds) {

		try {
			cadastroRestaurante.ativar(restauranteIds);
		} catch (RestauranteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@DeleteMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativarMultiplos(@RequestBody List<Long> restauranteIds) {
        
		try {
			cadastroRestaurante.inativar(restauranteIds);
		} catch (RestauranteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {

        cadastroRestaurante.excluir(id);
    }
}
