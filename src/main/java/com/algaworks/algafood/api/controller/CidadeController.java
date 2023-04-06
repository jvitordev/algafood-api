package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
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

import com.algaworks.algafood.api.ResourceUriHelper;
import com.algaworks.algafood.api.assembier.CidadeInputDisassembier;
import com.algaworks.algafood.api.assembier.CidadeModelAssembler;
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
    private CidadeModelAssembler cidadeModelAssembier;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CidadeModel> todas() {

        return cidadeModelAssembier.toCollectionModel(cidadeRepository.findAll());
    }
    
    @GetMapping("/{id}")
    public CidadeModel buscar(@PathVariable Long id) {

        CidadeModel cidadeModel = cidadeModelAssembier.toModel(cadastroCidade.buscarOuFalhar(id));

        cidadeModel.add(Link.of("http://api.algafood.local:8080/cidades/1"));
//		cidadeModel.add(Link.of("http://api.algafood.local:8080/cidades/1", IanaLinkRelations.SELF));

		// cidadeModel.add(Link.of("http://api.algafood.local:8080/cidades", IanaLinkRelations.COLLECTION));
		cidadeModel.add(Link.of("http://api.algafood.local:8080/cidades", "cidades"));

		cidadeModel.getEstado().add(Link.of("http://api.algafood.local:8080/estados/1"));

        return cidadeModel;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeModel adicionar(@RequestBody @Valid CidadeInput cidadeInput) {
        
        try {

            Cidade cidade = cidadeInputDisassembier.toDomainObject(cidadeInput);
            
            CidadeModel cidadeModel = cidadeModelAssembier.toModel(cadastroCidade.salvar(cidade));

            ResourceUriHelper.addUriInResponseHeader(cidadeModel.getId());

            return cidadeModel;
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
