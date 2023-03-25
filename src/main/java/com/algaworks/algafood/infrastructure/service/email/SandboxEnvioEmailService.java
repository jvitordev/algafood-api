package com.algaworks.algafood.infrastructure.service.email;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.algaworks.algafood.core.email.EmailProperties;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SandboxEnvioEmailService extends SmtpEnvioEmailService {
    
    @Autowired
	private JavaMailSender mailSender;

	@Autowired
	private EmailProperties emailProperties;
    
    @Override
    public void enviar(Mensagem mensagem) {
        
        try {
			String corpo = processarTemplate(mensagem);

			MimeMessage mimeMessage = mailSender.createMimeMessage();
			
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
			helper.setFrom(emailProperties.getSandbox().getDestinatario());
			helper.setTo(emailProperties.getSandbox().getDestinatario());
			helper.setSubject(mensagem.getAssunto());
			helper.setText(corpo, true);
			
			mailSender.send(mimeMessage);
			log.info("E-mail enviado");
		} catch (Exception e) {
			throw new EmailException("Não foi possível enviar e-mail", e);
		}
    }
}
