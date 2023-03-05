package com.algaworks.algafood.api.assembier;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.EstadoInput;
import com.algaworks.algafood.domain.model.Estado;

@Component
public class EstadoInputDisassembier {
    
    @Autowired
	ModelMapper modelMapper;

    public Estado toDomainObject(EstadoInput estadoInput) {
        
		return modelMapper.map(estadoInput, Estado.class); 
	}

	public void copyToDomainModel(EstadoInput estadoInput, Estado estado) {

		modelMapper.map(estadoInput, estado);
	}
}
