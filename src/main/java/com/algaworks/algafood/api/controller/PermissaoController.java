package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
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
    public ResponseEntity<Permissao> porId(@PathVariable Long id) {
        Optional<Permissao> permissao = permissaoRepository.findById(id);

        if (permissao.isPresent()) {

            return ResponseEntity.ok(permissao.get());
        }
        
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Permissao adicionar(@RequestBody Permissao permissao) {

        return cadastroPermissao.salvar(permissao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Permissao> editar(@PathVariable Long id, @RequestBody Permissao permissao) {
        Optional<Permissao> permissaoAtual = permissaoRepository.findById(id);

        if (permissaoAtual.isPresent()) {
            BeanUtils.copyProperties(permissao, permissaoAtual.get(), "id");
            Permissao permissaoSalva = cadastroPermissao.salvar(permissaoAtual.get());

            return ResponseEntity.ok(permissaoSalva);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id) {

        try {
            cadastroPermissao.excluir(id);

            return ResponseEntity.noContent().build();

        } catch (EntidadeNaoEncontradaException e) {

            return ResponseEntity.notFound().build();

        } catch (EntidadeEmUsoException e) {

            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}
