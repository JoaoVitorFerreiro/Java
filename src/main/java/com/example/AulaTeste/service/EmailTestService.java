package com.example.AulaTeste.service;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailTestService {

    @Autowired
    private EmailService emailService;

    public void testarEnvioEmail() {
        try {
            System.out.println("Iniciando teste de envio de email...");
            emailService.enviarEmailBoasVindas(
                    "jvferreiro1@gmail.com",
                    "Usuário Teste"
            );
            System.out.println("Teste concluído com sucesso!");
        } catch (Exception e) {
            System.err.println("Falha no teste de email:");
            e.printStackTrace();
        }
    }
}