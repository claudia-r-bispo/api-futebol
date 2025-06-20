package com.neoCamp.partidaFutebol.controller;

import com.neoCamp.partidaFutebol.dto.PartidaDTO;
import com.neoCamp.partidaFutebol.service.PartidaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/partidas")
public class PartidaController {

    private PartidaService partidaService;

    @PostMapping
    public ResponseEntity<PartidaDTO> createPartida(@RequestBody PartidaDTO dto) {
        PartidaDTO response = partidaService.createPartida(dto);
        return ResponseEntity.status(201).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PartidaDTO> updatePartida(@PathVariable Long id, @RequestBody PartidaDTO dto) {
        PartidaDTO response = partidaService.updatePartida(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePartida(@PathVariable Long id) {
        partidaService.removerPartida(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PartidaDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(partidaService.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<PartidaDTO>> listar(Pageable pageable) {
        return ResponseEntity.ok(partidaService.listar(pageable));
    }
}
