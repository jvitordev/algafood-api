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

import com.algaworks.algafood.api.assembier.FormaPagamentoInputDisassembier;
import com.algaworks.algafood.api.assembier.FormaPagamentoModelAssembier;
import com.algaworks.algafood.api.model.FormaPagamentoModel;
import com.algaworks.algafood.api.model.input.FormaPagamentoInput;
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

    @Autowired
    private FormaPagamentoModelAssembier formaPagamentoModelAssembier;

    @Autowired
    private FormaPagamentoInputDisassembier formaPagamentoInputDisassembier;

    @GetMapping
    public List<FormaPagamentoModel> todas() {
        
        return formaPagamentoModelAssembier.toCollectionModel(formaPagamentoRepository.findAll());
    }

    @GetMapping("/{id}")
    public FormaPagamentoModel buscar(@PathVariable Long id) {

        return formaPagamentoModelAssembier.toModel(cadastroFormaPagamento.buscarOuFalhar(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoModel adicionar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {

        FormaPagamento formaPagamento = formaPagamentoInputDisassembier.toDomainObject(formaPagamentoInput);

        return formaPagamentoModelAssembier.toModel(cadastroFormaPagamento.salvar(formaPagamento));
    }

    @PutMapping("{id}")
    public FormaPagamentoModel atualizar(
        @PathVariable Long id, 
        @RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {

        FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(id);

        formaPagamentoInputDisassembier.copyToDomainModel(formaPagamentoInput, formaPagamento);

        return formaPagamentoModelAssembier.toModel(cadastroFormaPagamento.salvar(formaPagamento));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {

        cadastroFormaPagamento.excluir(id);
    }

}
