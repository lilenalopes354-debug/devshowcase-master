package br.com.palm.devshowcase.controller;

import br.com.palm.devshowcase.dto.ProfileRequestDTO;
import br.com.palm.devshowcase.dto.ProfileResponseDTO;
import br.com.palm.devshowcase.service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {
    private final ProfileService service;

    public ProfileController(ProfileService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<ProfileResponseDTO> criar(@Valid @RequestBody ProfileRequestDTO dto) {
        var response = service.criar(dto);
        return ResponseEntity.created(URI.create("/api/profiles/" + response.id())).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileResponseDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }
}