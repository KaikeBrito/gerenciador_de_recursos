package com.example.gerenciamento_lar_francisco_de_assis.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gerenciamento_lar_francisco_de_assis.domain.dto.ItemEntradaDto;
import com.example.gerenciamento_lar_francisco_de_assis.domain.dto.ItemSaidaDto;
import com.example.gerenciamento_lar_francisco_de_assis.domain.dto.RegistrarEntradaDto;
import com.example.gerenciamento_lar_francisco_de_assis.domain.dto.RegistrarSaidaDto;
import com.example.gerenciamento_lar_francisco_de_assis.domain.entity.Doador;
import com.example.gerenciamento_lar_francisco_de_assis.domain.entity.EntradaDoacao;
import com.example.gerenciamento_lar_francisco_de_assis.domain.entity.ItemEntrada;
import com.example.gerenciamento_lar_francisco_de_assis.domain.entity.ItemSaida;
import com.example.gerenciamento_lar_francisco_de_assis.domain.entity.Localizacao;
import com.example.gerenciamento_lar_francisco_de_assis.domain.entity.Produto;
import com.example.gerenciamento_lar_francisco_de_assis.domain.entity.SaidaRecurso;
import com.example.gerenciamento_lar_francisco_de_assis.domain.entity.Usuario;
import com.example.gerenciamento_lar_francisco_de_assis.domain.exception.ResourceNotFoundException;
import com.example.gerenciamento_lar_francisco_de_assis.domain.respository.DoadorRepository;
import com.example.gerenciamento_lar_francisco_de_assis.domain.respository.EntradaDoacaoRepository;
import com.example.gerenciamento_lar_francisco_de_assis.domain.respository.ItemEntradaRepository;
import com.example.gerenciamento_lar_francisco_de_assis.domain.respository.ItemSaidaRepository;
import com.example.gerenciamento_lar_francisco_de_assis.domain.respository.LocalizacaoRepository;
import com.example.gerenciamento_lar_francisco_de_assis.domain.respository.ProdutoRepository;
import com.example.gerenciamento_lar_francisco_de_assis.domain.respository.SaidaRecursoRepository;
import com.example.gerenciamento_lar_francisco_de_assis.domain.respository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GestaoRecursosService {

    private final EntradaDoacaoRepository entradaDoacaoRepository;
    private final ItemEntradaRepository itemEntradaRepository;
    private final SaidaRecursoRepository saidaRecursoRepository;
    private final ItemSaidaRepository itemSaidaRepository;

    private final DoadorRepository doadorRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProdutoRepository produtoRepository;
    private final LocalizacaoRepository localizacaoRepository;

    private final EstoqueService estoqueService;


    @Transactional
    public EntradaDoacao registrarEntrada(RegistrarEntradaDto dto) {
        Usuario usuario = usuarioRepository.findById(dto.idUsuarioRegistro())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário de registro não encontrado."));
        
        Doador doador = null;
        if (dto.idDoador() != null) {
            doador = doadorRepository.findById(dto.idDoador())
                    .orElseThrow(() -> new ResourceNotFoundException("Doador não encontrado."));
        }

        EntradaDoacao entrada = EntradaDoacao.builder()
                .usuarioRegistro(usuario)
                .doador(doador)
                .observacoes(dto.observacoes())
                .build();
        
        EntradaDoacao entradaSalva = entradaDoacaoRepository.save(entrada);
        
        List<ItemEntrada> itensSalvos = new ArrayList<>();

        for (ItemEntradaDto itemDto : dto.itens()) {
            Produto produto = produtoRepository.findById(itemDto.idProduto())
                    .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado: ID " + itemDto.idProduto()));
            Localizacao local = localizacaoRepository.findById(itemDto.idLocalizacaoDestino())
                    .orElseThrow(() -> new ResourceNotFoundException("Localização não encontrada: ID " + itemDto.idLocalizacaoDestino()));

            ItemEntrada item = ItemEntrada.builder()
                    .entrada(entradaSalva)
                    .produto(produto)
                    .localizacaoDestino(local)
                    .quantidade(itemDto.quantidade())
                    .dataValidade(itemDto.dataValidade())
                    .build();
            itensSalvos.add(item);
            
            estoqueService.adicionarEstoque(produto, local, itemDto.quantidade());
        }

        itemEntradaRepository.saveAll(itensSalvos);
        
        return entradaSalva;
    }


    @Transactional
    public SaidaRecurso registrarSaida(RegistrarSaidaDto dto) {
        Usuario usuario = usuarioRepository.findById(dto.idUsuarioRegistro())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário de registro não encontrado."));

        SaidaRecurso saida = SaidaRecurso.builder()
                .usuarioRegistro(usuario)
                .destino(dto.destino())
                .justificativa(dto.justificativa())
                .build();
        
        SaidaRecurso saidaSalva = saidaRecursoRepository.save(saida);

        List<ItemSaida> itensSalvos = new ArrayList<>();

        for (ItemSaidaDto itemDto : dto.itens()) {
            Produto produto = produtoRepository.findById(itemDto.idProduto())
                    .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado: ID " + itemDto.idProduto()));
            Localizacao local = localizacaoRepository.findById(itemDto.idLocalizacaoOrigem())
                    .orElseThrow(() -> new ResourceNotFoundException("Localização não encontrada: ID " + itemDto.idLocalizacaoOrigem()));

            estoqueService.removerEstoque(produto, local, itemDto.quantidade());

            ItemSaida item = ItemSaida.builder()
                    .saida(saidaSalva)
                    .produto(produto)
                    .localizacaoOrigem(local)
                    .quantidade(itemDto.quantidade())
                    .build();
            itensSalvos.add(item);
        }

        itemSaidaRepository.saveAll(itensSalvos);

        return saidaSalva;
    }
}