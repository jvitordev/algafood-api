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

import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.PermissaoRepository;
import com.algaworks.algafood.domain.service.CadastroPermissaoService;

@RestController
@RequestMapping("/permissoes")
public class PermissaoController {

    @Autowired
    PermissaoRepository permissaoRepository;

    @Autowired
    CadastroPermissaoService cadastroPermissao;

    @GetMapping
    public List<Permissao> todas() {
        return permissaoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Permissao buscar(@PathVariable Long id) {

        return cadastroPermissao.buscarOuFalhar(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Permissao adicionar(@RequestBody Permissao permissao) {

        return cadastroPermissao.salvar(permissao);
    }

    @PutMapping("/{id}")
    public Permissao atualizar(@PathVariable Long id, @RequestBody Permissao permissao) {

        Permissao permissaoAtual = cadastroPermissao.buscarOuFalhar(id);

        BeanUtils.copyProperties(permissao, permissaoAtual, "id");

        return cadastroPermissao.salvar(permissaoAtual);

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {

        cadastroPermissao.excluir(id);
    }
}
