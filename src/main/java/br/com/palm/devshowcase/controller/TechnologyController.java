package br.com.palm.devshowcase.controller;

import br.com.palm.devshowcase.dto.TechnologyRequestDTO;
import br.com.palm.devshowcase.dto.TechnologyResponseDTO;
import br.com.palm.devshowcase.service.TechnologyService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/technologies")
public class TechnologyController {
    private final TechnologyService service;

    public TechnologyController(TechnologyService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<TechnologyResponseDTO> criar(@Valid @RequestBody TechnologyRequestDTO dto) {
        var response = service.criar(dto);
        return ResponseEntity.created(URI.create("/api/technologies/" + response.id())).body(response);
    }

    @GetMapping
    public ResponseEntity<List<TechnologyResponseDTO>> buscarTodos() {
        return ResponseEntity.ok(service.buscarTodos());
    }
}