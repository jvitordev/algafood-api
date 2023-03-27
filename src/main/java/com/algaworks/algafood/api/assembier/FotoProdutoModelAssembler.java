package com.algaworks.algafood.api.assembier;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.FotoProdutoModel;
import com.algaworks.algafood.domain.model.FotoProduto;

@Component
public class FotoProdutoModelAssembler {
    
    @Autowired
    ModelMapper modelMapper;
    
    public FotoProdutoModel toModel(FotoProduto foto) {
		
        return modelMapper.map(foto, FotoProdutoModel.class);
	}
}