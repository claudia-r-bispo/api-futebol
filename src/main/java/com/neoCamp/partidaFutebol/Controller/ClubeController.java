package com.neoCamp.partidaFutebol.Controller;

import com.neoCamp.partidaFutebol.Entity.Clube;
import com.neoCamp.partidaFutebol.Service.ClubeService;
import com.neoCamp.partidaFutebol.dto.ClubeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clubes")
public class ClubeController {

    @Autowired
    public ClubeService clubeService;
    // Implementar os métodos do controlador aqui, como criar, atualizar, listar clubes, etc.

    @PostMapping
    public ResponseEntity<ClubeDTO> createClube(@RequestBody @Validated ClubeDTO dto) {
        Clube clube = clubeService.createClube(dto);
        return ResponseEntity.status(201).body(new ClubeDTO(clube.getId(), clube.getNome(), clube.getUf(), clube.getDtCriacao(), clube.isAtivo()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClubeDTO> updateClube(@PathVariable Long id, @RequestBody @Validated ClubeDTO dto) {
        Clube clube = clubeService.updateClube(id, dto);
        return ResponseEntity.ok(new ClubeDTO(clube.getId(), clube.getNome(), clube.getUf(), clube.getDtCriacao(), clube.isAtivo()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ClubeDTO> deleteClube(@PathVariable Long id) {
        clubeService.inativar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClubeDTO> getClubeById(@PathVariable Long id) {
        Clube clube = clubeService.findById(id);
        return ResponseEntity.ok(new ClubeDTO(clube.getId(), clube.getNome(), clube.getUf(), clube.getDtCriacao(), clube.isAtivo()));
    }

    @GetMapping
    public ResponseEntity<Page<ClubeDTO>> listarClubes(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String uf,
            @RequestParam(required = false) Boolean ativo,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<Clube> clubes = clubeService.listarComFiltros(nome, uf, ativo, pageable);
        Page<ClubeDTO> clubeDTOs = clubes.map(clube -> new ClubeDTO(clube.getId(), clube.getNome(), clube.getUf(), clube.getDtCriacao(), clube.isAtivo()));
        return ResponseEntity.ok(clubeDTOs);
    }
}
