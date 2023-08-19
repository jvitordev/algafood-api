package com.algaworks.algafood.api.controller;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import com.algaworks.algafood.api.assembler.FormaPagamentoInputDisassembler;
import com.algaworks.algafood.api.assembler.FormaPagamentoModelAssembler;
import com.algaworks.algafood.api.model.FormaPagamentoModel;
import com.algaworks.algafood.api.model.input.FormaPagamentoInput;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafood.domain.service.CadastroFormaPagamentoService;

@RestController
@RequestMapping("/formas-de-pagamento")
public class FormaPagamentoController {
    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @Autowired
    private CadastroFormaPagamentoService cadastroFormaPagamento;

    @Autowired
    private FormaPagamentoModelAssembler formaPagamentoModelAssembler;

    @Autowired
    private FormaPagamentoInputDisassembler formaPagamentoInputDisassembler;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CollectionModel<FormaPagamentoModel>> listar(ServletWebRequest request) {
        
        String eTag = deepEtag(request, null);
		
		if (request.checkNotModified(eTag)) {
			return null;
		}

        List<FormaPagamento> todasFormasPagamentos = formaPagamentoRepository.findAll();

        CollectionModel<FormaPagamentoModel> formasPagamentosModel = formaPagamentoModelAssembler.toCollectionModel(todasFormasPagamentos);

        return ResponseEntity.ok()
            .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
            .eTag(eTag)
            .body(formasPagamentosModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FormaPagamentoModel> buscar(@PathVariable Long id, ServletWebRequest request) {

        String eTag = deepEtag(request, id);

        if (request.checkNotModified(eTag)) {
			return null;
		}

        FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(id);
        FormaPagamentoModel formaPagamentoModel = formaPagamentoModelAssembler.toModel(formaPagamento);

        return ResponseEntity.ok()
            .cacheControl(CacheControl.maxAge(15, TimeUnit.SECONDS))
            .eTag(eTag)
            .body(formaPagamentoModel);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoModel adicionar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {

        FormaPagamento formaPagamento = formaPagamentoInputDisassembler.toDomainObject(formaPagamentoInput);

        return formaPagamentoModelAssembler.toModel(cadastroFormaPagamento.salvar(formaPagamento));
    }

    @PutMapping("{id}")
    public FormaPagamentoModel atualizar(
        @PathVariable Long id, 
        @RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {

        FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(id);

        formaPagamentoInputDisassembler.copyToDomainModel(formaPagamentoInput, formaPagamento);

        return formaPagamentoModelAssembler.toModel(cadastroFormaPagamento.salvar(formaPagamento));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {

        cadastroFormaPagamento.excluir(id);
    }

    public String deepEtag(ServletWebRequest request, @Nullable Long id) {

        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
		
		String eTag = "0";
        OffsetDateTime dataUltimaAtualizacao;
		
        if (id != null) {
            
            dataUltimaAtualizacao = formaPagamentoRepository.getDataAtualizacaoById(id);
        } else {
            dataUltimaAtualizacao = formaPagamentoRepository.getDataUltimaAtualizacao();
        }
		
		if (dataUltimaAtualizacao != null) {
			eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
		}

        return eTag;
    }

}
