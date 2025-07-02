package com.neoCamp.footballMatch.controller;

import com.neoCamp.footballMatch.dto.StadiumDTO;
import com.neoCamp.footballMatch.service.StadiumService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estadios")

public class StadiumController {

    private StadiumService stadiumService;

    @PostMapping
    public ResponseEntity<StadiumDTO> createStadium(@RequestBody StadiumDTO dto) {
        StadiumDTO response = stadiumService.createEstadio(dto);
        return ResponseEntity.status(201).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StadiumDTO> updateStadium(@PathVariable Long id, @RequestBody StadiumDTO dto) {
        StadiumDTO response = stadiumService.updateEstadio(id, dto);
        return ResponseEntity.ok(response);
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

//createEstadio = createStadium
//updateSatadio = updateStadium