package com.algaworks.algafood.api.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@RestController
@RequestMapping("/teste")
public class TesteController {
    
    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    // método de teste para consulta JPQL
    @GetMapping("/cozinhas/por-nome")
    public List<Cozinha> cozinhasPorNome(String nome) {
        return cozinhaRepository.findTodasByNome(nome);
    }

   @GetMapping("/cozinhas/unica-por-nome")
   public Optional<Cozinha>  cozinhaPorNome(String nome) {
       return cozinhaRepository.findByNome(nome);
   }

   @GetMapping("/restaurantes/por-taxa-frete")
   public List<Restaurante> restaurantesPorTaxaFrete(BigDecimal taxaInicial, BigDecimal taxaFinal) {
       return restauranteRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal);
   }

   @GetMapping("/restaurantes/por-cozinha")
   public List<Restaurante> restaurantesPorCozinha(String nome, Long cozinhaId) {
       return restauranteRepository.procurarPorNome(nome, cozinhaId);
   }

   @GetMapping("/restaurantes/um-por-nome")
   public Optional<Restaurante> restauranteUmPorNome(String nome) {
       return restauranteRepository.findFirstByNomeContaining(nome);
   }

   @GetMapping("/restaurantes/top2-por-nome")
   public List<Restaurante> restauranteDoisPorNome(String nome) {
       return restauranteRepository.findTop2ByNomeContaining(nome);
   }
}
