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

import com.algaworks.algafood.api.assembier.CozinhaInputDisassembier;
import com.algaworks.algafood.api.assembier.CozinhaModelAssembier;
import com.algaworks.algafood.api.model.CozinhaModel;
import com.algaworks.algafood.api.model.input.CozinhaInput;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping(value = "/cozinhas") //, produces = MediaType.APPLICATION_XML_VALUE)
public class CozinhaController {
    
    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private CadastroCozinhaService cadastroCozinha;

    @Autowired
    CozinhaModelAssembier cozinhaModelAssembier;

    @Autowired
    CozinhaInputDisassembier cozinhaInputDisassembier;

    @GetMapping
    public List<CozinhaModel> todas() {
        return cozinhaModelAssembier.toCollectionModel(cozinhaRepository.findAll());
    }

    @GetMapping("/{cozinhaId}")
    public CozinhaModel buscar(@PathVariable Long cozinhaId) {

    	return cozinhaModelAssembier.toModel(cadastroCozinha.buscarOuFalhar(cozinhaId));
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public CozinhaModel adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {

        Cozinha cozinha = cozinhaInputDisassembier.toDomainObject(cozinhaInput);

        return cozinhaModelAssembier.toModel(cadastroCozinha.salvar(cozinha));
    }

    @PutMapping("/{cozinhaId}")
    public CozinhaModel atualizar(
        @PathVariable Long cozinhaId, 
        @Valid @RequestBody CozinhaInput cozinhaInput) {

        Cozinha cozinha = cadastroCozinha.buscarOuFalhar(cozinhaId);

        cozinhaInputDisassembier.copyToDomainModel(cozinhaInput, cozinha);

        return cozinhaModelAssembier.toModel(cadastroCozinha.salvar(cozinha));
    }

    @DeleteMapping("/{cozinhaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long cozinhaId) {

        cadastroCozinha.excluir(cozinhaId);
    }
}
