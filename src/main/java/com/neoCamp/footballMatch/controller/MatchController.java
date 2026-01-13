package com.neoCamp.footballMatch.controller;

import com.neoCamp.footballMatch.dto.rabbitmq.PartidaResultadoMessage;
import com.neoCamp.footballMatch.service.MatchMessageService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/matches")
@RequiredArgsConstructor
public class MatchController {

    private final MatchMessageService matchMessageService;

    @PostMapping("/{idMatch}/finish")
    public ResponseEntity<String> finishMatch(
            @PathVariable("idMatch") UUID idPartida,
            @RequestParam @NotBlank String winner,
            @RequestBody @NotEmpty @Valid List<PartidaResultadoMessage.PontuacaoMessage> scores) {
        try {
            matchMessageService.finishMatch(idPartida, winner, scores);
            return ResponseEntity.ok("Match finished successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error finishing match: " + e.getMessage());
        }
    }
}
