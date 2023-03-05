package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
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

import com.algaworks.algafood.api.assembier.RestauranteModelAssembier;
import com.algaworks.algafood.api.model.RestauranteModel;
import com.algaworks.algafood.api.model.input.RestauranteInput;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cozinha;
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

            Restaurante restaurante = toDomainObject(restauranteInput);
            
            return restauranteModelAssembier.toModel(cadastroRestaurante.salvar(restaurante));

        } catch (CozinhaNaoEncontradaException e) {

            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{id}")
    public RestauranteModel atualizar(
        @PathVariable Long id,
        @Valid @RequestBody RestauranteInput restauranteInput) {

        Restaurante restaurante = toDomainObject(restauranteInput);

        Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(id);

        BeanUtils.copyProperties(
            restaurante,
            restauranteAtual,
            "id", "formasPagamento", "endereco", "dataCadastro", "produtos"
        );

        try {

            return restauranteModelAssembier.toModel(cadastroRestaurante.salvar(restauranteAtual));

        } catch (CozinhaNaoEncontradaException e) {
            
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {

        cadastroRestaurante.excluir(id);
    }

    private Restaurante toDomainObject(RestauranteInput restauranteInput) {
        
		Restaurante restaurante = new Restaurante();
		restaurante.setNome(restauranteInput.getNome());
		restaurante.setTaxaFrete(restauranteInput.getTaxaFrete());
		
		Cozinha cozinha = new Cozinha();
		cozinha.setId(restauranteInput.getCozinha().getId());
		
		restaurante.setCozinha(cozinha);
		
		return restaurante;
	}
}
