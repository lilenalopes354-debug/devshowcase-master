package br.com.palm.devshowcase.controller;

import br.com.palm.devshowcase.dto.ProjectRequestDTO;
import br.com.palm.devshowcase.dto.ProjectResponseDTO;
import br.com.palm.devshowcase.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    private final ProjectService service;

    public ProjectController(ProjectService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<ProjectResponseDTO> criar(@Valid @RequestBody ProjectRequestDTO dto) {
        var response = service.criar(dto);
        return ResponseEntity.created(URI.create("/api/projects/" + response.id())).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }
}