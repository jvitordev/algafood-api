
package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.model.PermissaoModel;
import com.algaworks.algafood.domain.model.Permissao;

// Estendemos a classe RepresentationModelAssembler já que não precisaríamos customizar os links, assim como fizemos na classe CidadeModelAssembler.
// A classe RepresentationModel não possui o método toModel para customização, já a classe RepresentationModelAssemblerSupport possui.

@Component
public class PermissaoModelAssembler implements RepresentationModelAssembler<Permissao, PermissaoModel> {
    
	@Autowired
	private AlgaLinks algaLinks;

	@Autowired
    private ModelMapper modelMapper;
    
	@Override
    public PermissaoModel toModel(Permissao permissao) {
		
		PermissaoModel permissaoModel = modelMapper.map(permissao, PermissaoModel.class);
		
        return permissaoModel;
	}

    @Override
	public CollectionModel<PermissaoModel> toCollectionModel(Iterable<? extends Permissao> entities) {
    	return RepresentationModelAssembler.super.toCollectionModel(entities)
                .add(algaLinks.linkToPermissoes());
	}
}
