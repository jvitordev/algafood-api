package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

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

import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafood.domain.service.CadastroFormaPagamentoService;

@RestController
@RequestMapping("/formas-de-pagamento")
public class FormaPagamentoController {
    @Autowired
    FormaPagamentoRepository formaPagamentoRepository;

    @Autowired
    CadastroFormaPagamentoService cadastroFormaPagamento;

    @GetMapping
    public List<FormaPagamento> todas() {
        
        return formaPagamentoRepository.findAll();
    }

    @GetMapping("/{id}")
    public FormaPagamento buscar(@PathVariable Long id) {

        return cadastroFormaPagamento.buscarOuFalhar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamento adicionar(@RequestBody @Valid FormaPagamento formaPagamento) {

        return cadastroFormaPagamento.salvar(formaPagamento);
    }

    @PutMapping("{id}")
    public FormaPagamento atualizar(
        @PathVariable Long id, 
        @RequestBody @Valid FormaPagamento formaPagamento) {

        FormaPagamento formaPagamentoAtual = cadastroFormaPagamento.buscarOuFalhar(id);

        BeanUtils.copyProperties(
                formaPagamento,
                formaPagamentoAtual,
                "id"
        );

        return cadastroFormaPagamento.salvar(formaPagamentoAtual);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {

        cadastroFormaPagamento.excluir(id);
    }

}
