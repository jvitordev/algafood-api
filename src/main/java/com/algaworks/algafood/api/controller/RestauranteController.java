package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    RestauranteRepository restauranteRepository;

    @Autowired
    CadastroRestauranteService cadastroRestaurante;

    @Autowired
    CadastroCozinhaService cadastroCozinha;

    @GetMapping
    public List<Restaurante> todos() {
        return restauranteRepository.todos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurante> porId(@PathVariable Long id) {
        Restaurante restaurante = restauranteRepository.porId(id);

        if (restaurante != null) {
            return ResponseEntity.ok(restaurante);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante) {

        try {
            Restaurante restauranteAtual = cadastroRestaurante.salvar(restaurante);

            return ResponseEntity.status(HttpStatus.CREATED).body(restauranteAtual);

        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable Long id, @RequestBody Restaurante restaurante) {
        Restaurante restauranteAtual = restauranteRepository.porId(id);

        if (restauranteAtual != null) {

            BeanUtils.copyProperties(restaurante, restauranteAtual, "id");

            try {
                restaurante = cadastroRestaurante.salvar(restaurante);
                return ResponseEntity.ok(restaurante);
                
            } catch (EntidadeNaoEncontradaException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
    
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format(
            "Não existe cadastro de restaurante com o id %d", 
            id));
    }
}
