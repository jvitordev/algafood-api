package com.algaworks.algafood.api.assembier;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.GrupoInput;
import com.algaworks.algafood.domain.model.Grupo;

@Component
public class GrupoInputDisassembier {
    
    @Autowired
	ModelMapper modelMapper;

    public Grupo toDomainObject(GrupoInput grupoInput) {
        
		return modelMapper.map(grupoInput, Grupo.class); 
	}

	public void copyToDomainModel(GrupoInput grupoInput, Grupo grupo) {

		modelMapper.map(grupoInput, grupo);
	}
}
