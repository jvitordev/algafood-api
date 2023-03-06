package com.algaworks.algafood.api.assembier;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.FormaPagamentoInput;
import com.algaworks.algafood.domain.model.FormaPagamento;

@Component
public class FormaPagamentoInputDisassembier {
    
    @Autowired
	ModelMapper modelMapper;

    public FormaPagamento toDomainObject(FormaPagamentoInput formaPagamentoInput) {
        
		return modelMapper.map(formaPagamentoInput, FormaPagamento.class); 
	}

	public void copyToDomainModel(FormaPagamentoInput formaPagamentoInput, FormaPagamento formaPagamento) {

		modelMapper.map(formaPagamentoInput, formaPagamento);
	}
}
