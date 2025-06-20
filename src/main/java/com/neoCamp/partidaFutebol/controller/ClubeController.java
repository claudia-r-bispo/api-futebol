package com.neoCamp.partidaFutebol.controller;

import com.neoCamp.partidaFutebol.entity.ClubeEntity;
import com.neoCamp.partidaFutebol.service.ClubeService;
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
//public class ClubeController {
//
//
//    public ClubeService clubeService;
//
//
//    @PostMapping
//    public ResponseEntity<ClubeDTO> createClube(@RequestBody @Validated ClubeDTO dto) {
//        ClubeEntity clube = clubeService.createClube(dto);
//        return ResponseEntity.status(201).body(new ClubeDTO(clube.getId(), clube.getNome(), clube.getUf(), clube.getDtCriacao(), clube.isAtivo()));
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<ClubeDTO> updateClube(@PathVariable Long id, @RequestBody @Validated ClubeDTO dto) {
//        ClubeEntity response = clubeService.updateClube(id, dto);
//        return ResponseEntity.ok(response);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<ClubeDTO> deleteClube(@PathVariable Long id) {
//        clubeService.inativar(id);
//        return ResponseEntity.noContent().build();
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<ClubeDTO> getClubeById(@PathVariable Long id) {
//        ClubeEntity clube = clubeService.findById(id);
//        return ResponseEntity.ok(new ClubeDTO(clube.getId(), clube.getNome(), clube.getUf(), clube.getDtCriacao(), clube.isAtivo()));
//    }
//
//    @GetMapping
//    public ResponseEntity<Page<ClubeDTO>> listarClubes(
//            @RequestParam(required = false) String nome,
//            @RequestParam(required = false) String uf,
//            @RequestParam(required = false) Boolean ativo,
//            @PageableDefault(size = 10) Pageable pageable) {
//        Page<ClubeEntity> clubes = clubeService.listarComFiltros(nome, uf, ativo, pageable);
//        Page<ClubeDTO> clubeDTOs = clubes.map(clube -> new ClubeDTO(clube.getId(), clube.getNome(), clube.getUf(), clube.getDtCriacao(), clube.isAtivo()));
//        return ResponseEntity.ok(clubeDTOs);
//    }
//}

public class ClubeController {

    @Autowired
    private ClubeService clubeService;

    @PostMapping
    public ResponseEntity<ClubeDTO> createClube(@RequestBody @Validated ClubeDTO dto) {
        ClubeDTO response = clubeService.createClube(dto);
        return ResponseEntity.status(201).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClubeDTO> updateClube(@PathVariable Long id, @RequestBody @Validated ClubeDTO dto) {
        ClubeDTO response = clubeService.upDateClube(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClube(@PathVariable Long id) {
        clubeService.inativar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClubeDTO> getClubeById(@PathVariable Long id) {
        ClubeDTO response = clubeService.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<ClubeDTO>> listarClubes(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String uf,
            @RequestParam(required = false) Boolean ativo,
            @PageableDefault(size = 10) Pageable pageable
    ) {
        Page<ClubeDTO> clubes = clubeService.listarComFiltros(nome, uf, ativo, pageable);
        return ResponseEntity.ok(clubes);
    }
}