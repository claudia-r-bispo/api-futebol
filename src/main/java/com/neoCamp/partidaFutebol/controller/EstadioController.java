package com.neoCamp.partidaFutebol.controller;

import com.neoCamp.partidaFutebol.dto.EstadioDTO;
import com.neoCamp.partidaFutebol.service.EstadioService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estadios")

public class EstadioController {

    private EstadioService estadioService;

    @PostMapping
    public ResponseEntity<EstadioDTO> createEstadio(@RequestBody EstadioDTO dto) {
        EstadioDTO response = estadioService.createEstadio(dto);
        return ResponseEntity.status(201).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstadioDTO> updateEstadio(@PathVariable Long id, @RequestBody EstadioDTO dto) {
        EstadioDTO response = estadioService.updateEstadio(id, dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstadioDTO> getById(@PathVariable Long id) {
        EstadioDTO response = estadioService.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<EstadioDTO>> listar(Pageable pageable) {
        return ResponseEntity.ok(estadioService.listar(pageable));
    }
}

