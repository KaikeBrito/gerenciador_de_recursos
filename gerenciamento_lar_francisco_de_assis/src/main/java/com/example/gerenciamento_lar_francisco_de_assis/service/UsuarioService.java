package com.example.gerenciamento_lar_francisco_de_assis.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gerenciamento_lar_francisco_de_assis.domain.dto.RegisterRequestDto;
import com.example.gerenciamento_lar_francisco_de_assis.domain.dto.UsuarioDto;
import com.example.gerenciamento_lar_francisco_de_assis.domain.entity.Usuario;
import com.example.gerenciamento_lar_francisco_de_assis.domain.enums.PapelUsuario;
import com.example.gerenciamento_lar_francisco_de_assis.domain.exception.BusinessRuleException;
import com.example.gerenciamento_lar_francisco_de_assis.domain.exception.ResourceNotFoundException;
import com.example.gerenciamento_lar_francisco_de_assis.domain.mapper.UsuarioMapper;
import com.example.gerenciamento_lar_francisco_de_assis.domain.respository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + id));
    }

    @Transactional
    public Usuario criar(UsuarioDto dto) {
        usuarioRepository.findByEmail(dto.email()).ifPresent(u -> {
            throw new BusinessRuleException("Email '" + dto.email() + "' já cadastrado.");
        });

        Usuario usuario = usuarioMapper.toEntity(dto, null);
        
        String senhaHash = passwordEncoder.encode(dto.senha());
        usuario.setSenha(senhaHash);

        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario atualizar(Long id, UsuarioDto dto) {
        Usuario usuarioExistente = buscarPorId(id);

        usuarioRepository.findByEmail(dto.email()).ifPresent(u -> {
            if (!u.getId().equals(id)) {
                throw new BusinessRuleException("Email '" + dto.email() + "' já cadastrado.");
            }
        });

        Usuario usuarioAtualizado = usuarioMapper.toEntity(dto, usuarioExistente);

        if (dto.senha() != null && !dto.senha().isEmpty()) {
        	String senhaHash = passwordEncoder.encode(dto.senha());
        	usuarioAtualizado.setSenha(senhaHash);
        }
        
        return usuarioRepository.save(usuarioAtualizado);
    }

    @Transactional
    public void deletar(Long id) {
        Usuario usuario = buscarPorId(id);
        
        if (usuario.getPapel() == PapelUsuario.ADMIN && usuarioRepository.countByPapel(PapelUsuario.ADMIN) == 1) {
            throw new BusinessRuleException("Não pode deletar o último administrador.");
        }
        
        usuarioRepository.delete(usuario);
    }
    
    @Transactional
    public Usuario registrarNovoUsuario(RegisterRequestDto dto) {
        
        // 1. ROBUSTEZ: Validação de Negócio (E-mail duplicado)
        usuarioRepository.findByEmail(dto.email()).ifPresent(u -> {
            throw new BusinessRuleException("Email '" + dto.email() + "' já está cadastrado.");
        });

        // 2. ROBUSTEZ: Criptografia da Senha
        String senhaHash = passwordEncoder.encode(dto.senha());

        // 3. Criação da Entidade
        Usuario novoUsuario = Usuario.builder()
                .nomeCompleto(dto.nomeCompleto())
                .email(dto.email())
                .senha(senhaHash)
                // 4. ROBUSTEZ: Define um papel padrão.
                // Nunca permita que um usuário se cadastre como ADMIN.
                .papel(PapelUsuario.ESTOQUISTA) 
                .build();

        // 5. Salva no banco
        return usuarioRepository.save(novoUsuario);
    }
}