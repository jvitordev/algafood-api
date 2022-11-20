package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {
    
    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CadastroEstadoService cadastroEstado;

    @GetMapping()
    public List<Estado> todos() {
        return estadoRepository.todos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estado> porId(@PathVariable Long id) {

        try {
            Estado estado = estadoRepository.porId(id);
            return ResponseEntity.ok(estado);

        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
	public ResponseEntity<Estado> adicionar(@RequestBody Estado estado) {

        estado = cadastroEstado.salvar(estado);

        return ResponseEntity.ok(estado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estado> editar(@PathVariable Long id, @RequestBody Estado estado){
            Estado estadoAtual = estadoRepository.porId(id);

            if (estadoAtual != null) {

                BeanUtils.copyProperties(estado, estadoAtual, "id");
                estadoAtual = cadastroEstado.salvar(estadoAtual);

                return ResponseEntity.ok(estadoAtual);
            }

            return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
	public ResponseEntity<?> remover(@PathVariable Long id){
        
        try {
    
            cadastroEstado.excluir(id);

            return ResponseEntity.noContent().build();
    
            
        } catch (EntidadeNaoEncontradaException e) {
            
            return ResponseEntity.notFound().build();

        } catch (EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}
