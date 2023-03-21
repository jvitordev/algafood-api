package com.algaworks.algafood.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.ProdutoNaoEncontradoException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;

@Service
public class CadastroProdutoService {

    private static final String MSG_PRODUTO_EM_USO 
        = "O produto de id %d não pode ser excluído, pois está em uso!";

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CatalogoFotoProdutoService catalogoFotoProduto;

    @Transactional
    public Produto salvar(Produto produto) {

        return produtoRepository.save(produto);
    }

    @Transactional
    public void excluir(Long restauranteId, Long produtoId) {
        try {

            Optional<FotoProduto> fotoProduto = produtoRepository.findFotoById(restauranteId, produtoId);

            if(fotoProduto.isPresent()){

                catalogoFotoProduto.remover(restauranteId, produtoId);
            }

            produtoRepository.deleteById(produtoId);
            produtoRepository.flush();

        } catch (EmptyResultDataAccessException e) {

            throw new ProdutoNaoEncontradoException(produtoId);

        } catch (DataIntegrityViolationException e) {

            throw new EntidadeEmUsoException(
                    String.format(MSG_PRODUTO_EM_USO, produtoId));
        }
    }

    public Produto buscarOuFalhar(Long restauranteId, Long produtoId) {
    
        return produtoRepository.findById(restauranteId, produtoId).orElseThrow(
            () -> new ProdutoNaoEncontradoException(produtoId));
    }
}
