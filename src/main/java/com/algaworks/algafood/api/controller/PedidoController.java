package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembier.PedidoModelAssembler;
import com.algaworks.algafood.api.model.PedidoModel;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.service.EmissaoPedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    PedidoRepository pedidoRepository;
    
    @Autowired
    private EmissaoPedidoService cadastroPedido;

    @Autowired
    PedidoModelAssembler pedidoModelAssembler;

    @GetMapping
    public List<PedidoModel> todos() {

        return pedidoModelAssembler.toCollectionModel(pedidoRepository.findAll());
    }

    @GetMapping("/{pedidoId}")
    public PedidoModel buscar(@PathVariable Long pedidoId) {
            
        return pedidoModelAssembler.toModel(cadastroPedido.buscarOuFalhar(pedidoId));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {

        cadastroPedido.excluir(id);
    }
}
