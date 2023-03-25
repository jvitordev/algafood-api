package com.algaworks.algafood.core.email;

import javax.validation.constraints.NotBlank;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

@Validated
@Getter
@Setter
@Component
@ConfigurationProperties("algafood.email")
public class EmailProperties {
    
    @NotBlank
    private String remetente;
    private TipoEmail impl = TipoEmail.FAKE;

    public enum TipoEmail {

        SMTP,
        FAKE
    }
}
