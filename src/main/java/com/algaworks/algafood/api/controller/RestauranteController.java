package com.algaworks.algafood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.core.validation.ValidacaoException;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    RestauranteRepository restauranteRepository;

    @Autowired
    CadastroRestauranteService cadastroRestaurante;

    @Autowired
    CadastroCozinhaService cadastroCozinha;

    @Autowired
    SmartValidator validator;

    private Field field;

    @GetMapping
    public List<Restaurante> todos() {

        return restauranteRepository.findAll();
    }

    @GetMapping("/{id}")
    public Restaurante buscar(@PathVariable Long id) {
            
        return cadastroRestaurante.buscarOuFalhar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Restaurante adicionar(
        @RequestBody @Valid Restaurante restaurante) {

        try {
            
            return cadastroRestaurante.salvar(restaurante);

        } catch (CozinhaNaoEncontradaException e) {

            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{id}")
    public Restaurante atualizar(
        @PathVariable Long id, @Valid @RequestBody Restaurante restaurante) {

        Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(id);

        BeanUtils.copyProperties(
            restaurante,
            restauranteAtual,
            "id", "formasPagamento", "endereco", "dataCadastro", "produtos"
        );

        try {

            return cadastroRestaurante.salvar(restauranteAtual);

        } catch (CozinhaNaoEncontradaException e) {
            
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PatchMapping("/{id}")
    public Restaurante atualizarParcial(
        @PathVariable Long id, 
        @RequestBody Map<String, Object> campos,
        HttpServletRequest request
    ) 
    {
        Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(id);

        merge(campos, restauranteAtual, request);
        validate(restauranteAtual, "restaurante");

        return atualizar(id, restauranteAtual);
    }

    private void merge(
        Map<String, Object> dadosOrigem, 
        Restaurante restauranteDestino, 
        HttpServletRequest request
        ) {

            ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);
            
        try {

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
            
            Restaurante restauranteOrigem = objectMapper.convertValue(
                dadosOrigem, 
                Restaurante.class
            );
    
            dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
                field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
                field.setAccessible(true);
    
                Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
    
                ReflectionUtils.setField(field, restauranteDestino, novoValor);
            });
        } catch (IllegalArgumentException e) {
            
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
        }
    }

    private void validate(Restaurante restaurante, String objectName){

        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurante, objectName);
        validator.validate(restaurante, bindingResult);

        if (bindingResult.hasErrors()) {
            
            throw new ValidacaoException(bindingResult);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {

        cadastroRestaurante.excluir(id);
    }
}
