package com.example.gerenciamento_lar_francisco_de_assis.controller;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gerenciamento_lar_francisco_de_assis.domain.dto.LoginRequestDto;
import com.example.gerenciamento_lar_francisco_de_assis.domain.dto.LoginResponseDto;
import com.example.gerenciamento_lar_francisco_de_assis.domain.dto.RegisterRequestDto;
import com.example.gerenciamento_lar_francisco_de_assis.domain.dto.UsuarioResponseDto;
import com.example.gerenciamento_lar_francisco_de_assis.domain.entity.Usuario;
import com.example.gerenciamento_lar_francisco_de_assis.service.JwtTokenService;
import com.example.gerenciamento_lar_francisco_de_assis.service.UsuarioService;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "1. Autenticação", description = "Endpoints para login e registro de usuários")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final UsuarioService usuarioService;
    
    @Operation(
        summary = "Login de Usuário",
        description = "Autentica um usuário e retorna um token JWT."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login bem-sucedido, token retornado"),
        @ApiResponse(responseCode = "400", description = "Requisição inválida (ex: e-mail ou senha faltando)"),
        @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto dto) {
        // 1. O AuthenticationManager usará nosso CustomUserDetailsService e PasswordEncoder
        // para validar o email e senha.
    	Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.email(), dto.senha())
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtTokenService.generateToken(userDetails);
        return ResponseEntity.ok(new LoginResponseDto(token));
    }
    
    @Operation(
        summary = "Registrar Novo Usuário",
        description = "Registra um novo usuário com papel padrão (ESTOQUISTA)."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuário registrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos (ex: e-mail já existe, senha fraca)")
    })
    @PostMapping("/register")
    public ResponseEntity<UsuarioResponseDto> registrarUsuario(@Valid @RequestBody RegisterRequestDto dto) {
        
        // 1. Chama o serviço robusto que criamos
        Usuario usuarioSalvo = usuarioService.registrarNovoUsuario(dto);

        // 2. Mapeia para um DTO de Resposta (para não vazar a senha)
        UsuarioResponseDto responseDto = new UsuarioResponseDto(
                usuarioSalvo.getId(),
                usuarioSalvo.getNomeCompleto(),
                usuarioSalvo.getEmail(),
                usuarioSalvo.getPapel()
        );
        
        // 3. Retorna 201 Created com o usuário criado (sem a senha)
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
}