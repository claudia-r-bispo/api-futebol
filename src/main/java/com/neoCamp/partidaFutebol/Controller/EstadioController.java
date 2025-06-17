package com.neoCamp.partidaFutebol.Controller;

import com.neoCamp.partidaFutebol.Entity.Estadio;
import com.neoCamp.partidaFutebol.Repository.EstadioRepository;
import com.neoCamp.partidaFutebol.dto.EstadioDTO;
import jakarta.validation.Valid;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estadios")
public class EstadioController  {

    private final EstadioRepository estadioRepository;


    public EstadioController(EstadioRepository estadioRepository) {
        this.estadioRepository = estadioRepository;
    }

    @PostMapping
    public ResponseEntity<Estadio> createEstadio(@RequestBody @Valid EstadioDTO dto) {
        Estadio estadio = new Estadio();
        estadio.setNome(dto.getNome());
         estadio.setUf(dto.getUf());
         estadio.setDtCriacao(dto.getDtCriacao());
         estadio.setAtivo(true);
        return ResponseEntity.ok(estadioRepository.save(estadio));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estadio> updateEstadio(@PathVariable Long id, @RequestBody @Valid EstadioDTO dto) {
        Estadio estadio = estadioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estádio não encontrado com o ID: " + id));
        estadio.setNome(dto.getNome());
        // estadio.setUf(dto.getUf());
        // estadio.setDtCriacao(dto.getDtCriacao());
        // estadio.setAtivo(dto.isAtivo());
        return ResponseEntity.ok(estadioRepository.save(estadio));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estadio> findById(@PathVariable Long id) {
        Estadio estadio = estadioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estádio não encontrado!"));
        return ResponseEntity.ok(estadio);
    }

    public Page<Estadio> listar(Pageable pageable) {
        return estadioRepository.findAll(pageable);
    }
}

