package com.neoCamp.footballMatch.controller;

import com.neoCamp.footballMatch.dto.FootballMatchDTO;
import com.neoCamp.footballMatch.service.FootballMatchService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/partidas")
public class FootballMatchController {

    private final FootballMatchService footballMatchService;

    public FootballMatchController(FootballMatchService footballMatchService) {
        this.footballMatchService = footballMatchService;
    }

    @PostMapping
    public ResponseEntity<FootballMatchDTO> createFootballMatch(@RequestBody FootballMatchDTO dto) {
        FootballMatchDTO response = footballMatchService.create(dto);
        return ResponseEntity.status(201).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FootballMatchDTO> updateFootballMatch(
            @PathVariable UUID id, 
            @RequestBody FootballMatchDTO dto) {
        FootballMatchDTO response = footballMatchService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFootballMatch(@PathVariable UUID id) {
        footballMatchService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FootballMatchDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(footballMatchService.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<FootballMatchDTO>> list(Pageable pageable) {
        return ResponseEntity.ok(footballMatchService.listar(pageable));
    }
}
