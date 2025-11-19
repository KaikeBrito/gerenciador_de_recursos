package com.example.gerenciamento_lar_francisco_de_assis.service;

import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gerenciamento_lar_francisco_de_assis.domain.entity.Usuario;
import com.example.gerenciamento_lar_francisco_de_assis.domain.respository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // 1. Encontra o usuário no seu repositório pelo email
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> 
                        new UsernameNotFoundException("Usuário não encontrado com o email: " + email));

        // 2. Converte o Papel (Enum) em uma "Authority" (Autoridade) do Spring
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + usuario.getPapel().name());

        // 3. Retorna o "User" que o Spring Security entende
        // Ele usa este objeto para comparar a senha (hash)
        return new User(
                usuario.getEmail(),
                usuario.getSenha(),
                Collections.singletonList(authority)
        );
    }
}