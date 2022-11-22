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
    public ResponseEntity<FormaPagamento> porId(@PathVariable Long id) {
        Optional<FormaPagamento> formaPagamento = formaPagamentoRepository.findById(id);

        if (formaPagamento.isPresent()) {
            return ResponseEntity.ok(formaPagamento.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamento adicionar(@RequestBody FormaPagamento formaPagamento) {

        return cadastroFormaPagamento.salvar(formaPagamento);
    }

    @PutMapping("{id}")
    public ResponseEntity<FormaPagamento> editar(@PathVariable Long id, @RequestBody FormaPagamento formaPagamento) {
        Optional<FormaPagamento> formaPagamentoAtual = formaPagamentoRepository.findById(id);

        if (formaPagamentoAtual.isPresent()) {
            BeanUtils.copyProperties(formaPagamento, formaPagamentoAtual.get(),"id");
            FormaPagamento formaPagamentoSalvo = cadastroFormaPagamento.salvar(formaPagamentoAtual.get());

            return ResponseEntity.ok(formaPagamentoSalvo);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id) {

        try {
            cadastroFormaPagamento.excluir(id);

            return ResponseEntity.noContent().build();

        } catch (EntidadeNaoEncontradaException e) {

            return ResponseEntity.notFound().build();

        } catch (EntidadeEmUsoException e) {

            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

}
