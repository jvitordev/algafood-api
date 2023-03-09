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

import com.algaworks.algafood.api.assembier.GrupoInputDisassembier;
import com.algaworks.algafood.api.assembier.GrupoModelAssembler;
import com.algaworks.algafood.api.model.GrupoModel;
import com.algaworks.algafood.api.model.input.GrupoInput;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.repository.GrupoRepository;
import com.algaworks.algafood.domain.service.CadastroGrupoService;

@RestController
@RequestMapping("/grupos")
public class GrupoController {
    
    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private CadastroGrupoService cadastroGrupo;

    @Autowired
    private GrupoInputDisassembier grupoInputDisassembier;

    @Autowired
    private GrupoModelAssembler grupoModelAssembier;

    @GetMapping()
    public List<GrupoModel> todos() {

        return grupoModelAssembier.toCollectionModel(grupoRepository.findAll());
    }

    @GetMapping("/{id}")
    public GrupoModel buscar(@PathVariable Long id) {

        return grupoModelAssembier.toModel(cadastroGrupo.buscarOuFalhar(id));
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public GrupoModel adicionar(@RequestBody @Valid GrupoInput grupoInput) {

        Grupo grupo = grupoInputDisassembier.toDomainObject(grupoInput);

        return grupoModelAssembier.toModel(cadastroGrupo.salvar(grupo));
    }

    @PutMapping("/{id}")
    public GrupoModel atualizar(@PathVariable Long id, @RequestBody @Valid GrupoInput grupoInput) {

        Grupo grupo = cadastroGrupo.buscarOuFalhar(id);

        grupoInputDisassembier.copyToDomainModel(grupoInput, grupo);

        return grupoModelAssembier.toModel(cadastroGrupo.salvar(grupo));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {

        cadastroGrupo.excluir(id);
    }
}
