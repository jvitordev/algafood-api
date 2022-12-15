package com.algaworks.algafood.api.controller;

import java.util.List;

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

import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    CidadeRepository cidadeRepository;

    @Autowired
    CadastroCidadeService cadastroCidade;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Cidade> todas() {
        return cidadeRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public Cidade porId(@PathVariable Long id) {

        return cadastroCidade.buscarOuFalhar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cidade adicionar(@RequestBody Cidade cidade) {
        
        return cadastroCidade.salvar(cidade);
    }

    @PutMapping("/{id}")
    public Cidade atualizar(@PathVariable Long id, @RequestBody Cidade cidade) {

        Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(id);

        BeanUtils.copyProperties(cidade, cidadeAtual, "id");

        return cadastroCidade.salvar(cidadeAtual);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
           
        cadastroCidade.excluir(id);
    }
}
