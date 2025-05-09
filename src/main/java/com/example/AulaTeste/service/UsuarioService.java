package com.example.AulaTeste.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.AulaTeste.errors.UsuarioJaExiste;
import com.example.AulaTeste.model.UserModel;
import com.example.AulaTeste.repository.IUserRepository;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.transaction.Transactional;
@Service
public class UsuarioService {
    @Autowired
    private IUserRepository usuarioRepository;

    @Autowired
    private EmailService emailService;

    public UserModel criarUsuario(UserModel userModel) {
        var userExistente = usuarioRepository.findByEmail(userModel.getEmail());
        if (userExistente != null) {
            throw new UsuarioJaExiste();
        }

        String senhaCriptografada = BCrypt.withDefaults()
                .hashToString(12, userModel.getSenha().toCharArray());
        userModel.setSenha(senhaCriptografada);

        UserModel usuarioCriado = usuarioRepository.save(userModel);

        try {
            emailService.enviarEmailBoasVindas(
                    usuarioCriado.getEmail(),
                    usuarioCriado.getNome()
            );
        } catch (Exception e) {
            System.err.println("Erro ao enviar email: " + e.getMessage());
        }

        return usuarioCriado;
    }

    public List<UserModel> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public UserModel buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public boolean autenticar(String email, String senha) {
        UserModel usuario = usuarioRepository.findByEmail(email);
        if (usuario == null) return false;

        return BCrypt.verifyer().verify(senha.toCharArray(), usuario.getSenha()).verified;
    }

    @Transactional
    public void deletarPorEmail(String email) {
        UserModel usuario = usuarioRepository.findByEmail(email);
        if (usuario == null) {
            throw new RuntimeException("Usuário não encontrado");
        }
        usuarioRepository.delete(usuario);
    }
}
