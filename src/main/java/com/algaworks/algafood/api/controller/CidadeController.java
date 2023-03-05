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

import com.algaworks.algafood.api.assembier.CidadeInputDisassembier;
import com.algaworks.algafood.api.assembier.CidadeModelAssembier;
import com.algaworks.algafood.api.model.CidadeModel;
import com.algaworks.algafood.api.model.input.CidadeInput;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
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

    @Autowired
    private CidadeInputDisassembier cidadeInputDisassembier;

    @Autowired
    private CidadeModelAssembier cidadeModelAssembier;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CidadeModel> todas() {

        return cidadeModelAssembier.toCollectionModel(cidadeRepository.findAll());
    }
    
    @GetMapping("/{id}")
    public CidadeModel buscar(@PathVariable Long id) {

        return cidadeModelAssembier.toModel(cadastroCidade.buscarOuFalhar(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeModel adicionar(@RequestBody @Valid CidadeInput cidadeInput) {
        
        try {

            Cidade cidade = cidadeInputDisassembier.toDomainObject(cidadeInput);
            
            return cidadeModelAssembier.toModel(cadastroCidade.salvar(cidade));

        } catch (EstadoNaoEncontradoException e) {
            
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{id}")
    public CidadeModel atualizar(@PathVariable Long id, @RequestBody @Valid CidadeInput cidadeInput) {

        try {
            
            Cidade cidade = cadastroCidade.buscarOuFalhar(id);

            cidadeInputDisassembier.copyToDomainModel(cidadeInput, cidade);

            return cidadeModelAssembier.toModel(cadastroCidade.salvar(cidade));

        } catch (EstadoNaoEncontradoException e) {

            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
           
        cadastroCidade.excluir(id);
    }
}
