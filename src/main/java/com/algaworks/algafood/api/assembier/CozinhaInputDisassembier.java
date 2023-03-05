package com.algaworks.algafood.api.assembier;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.CozinhaInput;
import com.algaworks.algafood.domain.model.Cozinha;

@Component
public class CozinhaInputDisassembier {
    

    @Autowired
	ModelMapper modelMapper;

    public Cozinha toDomainObject(CozinhaInput cozinhaInput) {
        
		return modelMapper.map(cozinhaInput, Cozinha.class); 
	}

	public void copyToDomainModel(CozinhaInput cozinhaInput, Cozinha cozinha) {

		modelMapper.map(cozinhaInput, cozinha);
	}
}
