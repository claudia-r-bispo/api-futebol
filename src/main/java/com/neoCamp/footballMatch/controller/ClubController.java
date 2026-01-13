package com.neoCamp.footballMatch.controller;

import com.neoCamp.footballMatch.service.ClubService;
import com.neoCamp.footballMatch.dto.ClubDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/clubes")
@RequiredArgsConstructor
public class ClubController {

    private final ClubService clubService;

    @PostMapping
    public ResponseEntity<ClubDTO> createClube(@RequestBody @Validated ClubDTO dto) {
        try {
            ClubDTO response = clubService.createClube(dto);
            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClubDTO> updateClub(
            @PathVariable UUID id,
            @RequestBody @Validated ClubDTO dto) {
        try {
            ClubDTO response = clubService.updateClube(id, dto);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClub(@PathVariable UUID id) {
        try {
            clubService.inativar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClubDTO> getClubById(@PathVariable UUID id) {
        try {
            ClubDTO response = clubService.findById(id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<Page<ClubDTO>> listClubs(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String uf,
            @RequestParam(required = false) Boolean ativo,
            @PageableDefault(size = 10) Pageable pageable
    ) {
        try {
            Page<ClubDTO> clubes = clubService.listClubsWithFilters(nome, uf, ativo, pageable);
            return ResponseEntity.ok(clubes);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}