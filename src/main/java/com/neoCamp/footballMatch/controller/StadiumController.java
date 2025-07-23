package com.neoCamp.footballMatch.controller;

import com.neoCamp.footballMatch.dto.StadiumDTO;
import com.neoCamp.footballMatch.response.ViaCepResponse;
import com.neoCamp.footballMatch.service.StadiumService;
import com.neoCamp.footballMatch.service.ViaCepService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/estadios")


public class StadiumController {

    private final StadiumService stadiumService;
    private final ViaCepService viaCepService;

    public StadiumController(StadiumService stadiumService, ViaCepService viaCepService) {
        this.stadiumService = stadiumService != null ? stadiumService : null;
        this.viaCepService = viaCepService != null ? viaCepService : null;
    }

    @PostMapping
    public ResponseEntity<StadiumDTO> createStadium(@RequestBody StadiumDTO dto) {
        try {
            StadiumDTO response = stadiumService.createEstadio(dto);
            return ResponseEntity.status(201).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<StadiumDTO> updateStadium(@PathVariable Long id, @RequestBody StadiumDTO dto) {
        StadiumDTO response = stadiumService.updateEstadio(id, dto);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/validate-cep/{cep}")
    public ResponseEntity<ViaCepResponse> validateCep(@PathVariable String cep) {
        try {
            ViaCepResponse response = viaCepService.buscarCep(cep);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<StadiumDTO> getById(@PathVariable Long id) {
        StadiumDTO response = stadiumService.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<StadiumDTO>> list(Pageable pageable) {
        return ResponseEntity.ok(stadiumService.listar(pageable));
    }
}

