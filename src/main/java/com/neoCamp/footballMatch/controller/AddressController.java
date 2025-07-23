package com.neoCamp.footballMatch.controller;

import com.neoCamp.footballMatch.entity.AddressEntity;
import com.neoCamp.footballMatch.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<AddressEntity> create(@RequestBody AddressEntity address) {
        return ResponseEntity.ok(addressService.save(address));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressEntity> getById(@PathVariable Long id) {
        return addressService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<AddressEntity> getAll() {
        return addressService.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        addressService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
