package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.model.CozinhasXmlWapper;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

@RestController
@RequestMapping(value = "/cozinhas") //, produces = MediaType.APPLICATION_XML_VALUE)
public class CozinhaController {
    
    @Autowired
    private CozinhaRepository cozinhaRepository;

    @GetMapping
    public List<Cozinha> todas() {
        return cozinhaRepository.todas();
    }

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public CozinhasXmlWapper todasXml() {
        return new CozinhasXmlWapper(cozinhaRepository.todas());
    }

    @GetMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> porId(@PathVariable(value = "cozinhaId", required = false) Long id) {
        Cozinha cozinha =  cozinhaRepository.porId(id);


        // Editando o status e o corpo da resposta HTTP

        // return ResponseEntity.status(HttpStatus.OK).build(); // STATUS + CORPO VAZIO
        // return ResponseEntity.status(HttpStatus.OK).body(cozinha); // STATUS + CORPO
        // return ResponseEntity.ok(cozinha); // ATALHO PARA STATUS + CORPO

        // Editando o Header da resposta HTTP

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, "http://api.algafood.local:8080/cozinhas");

        return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
    }
}
