package com.algaworks.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    
    MENSAGEM_INCONPREENSIVEL("Mensagem inconpreensível", "/mensagem-incompreensivel"),
    ENTIDADE_NAO_ENCONTRADA("Entidade não encontrada", "/entidade-nao-encontada"),
    ENTIDADE_EM_USO("Entidade em uso", "/entidade-em-uso"),
    ERRO_NEGOCIO("Violação de regra de negócio", "/erro-negocio");

    private String title;
    private String uri;

    ProblemType(String title, String path) {
        this.title = title;
        this.uri = "http://api.algafood.local:8080" + path;
    }
}
