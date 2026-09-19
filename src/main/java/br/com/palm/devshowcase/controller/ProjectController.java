package br.com.palm.devshowcase.controller;

import br.com.palm.devshowcase.dto.ProjectRequestDTO;
import br.com.palm.devshowcase.dto.ProjectResponseDTO;
import br.com.palm.devshowcase.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import java.net.URI;

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
    public ResponseEntity<?> listar(
        @RequestParam(required = false) String technology,
        @RequestParam(required = false) Integer page,
        @RequestParam(required = false) Integer size) {
        if (technology == null && page == null && size == null) {
            return ResponseEntity.ok(service.listar());
        }
        int currentPage = page == null ? 0 : page;
        int pageSize = size == null ? 10 : size;
        if (currentPage < 0 || pageSize < 1 || pageSize > 100) {
            throw new IllegalArgumentException("page deve ser >= 0 e size deve estar entre 1 e 100");
        }
        var pageable = PageRequest.of(currentPage, pageSize, Sort.by("id").ascending());
        return ResponseEntity.ok(service.listar(technology, pageable));
    }

    @PostMapping("/{id}/feedbacks")
    public ResponseEntity<br.com.palm.devshowcase.dto.FeedbackResponseDTO> adicionarFeedback(
        @PathVariable Long id, @Valid @RequestBody br.com.palm.devshowcase.dto.FeedbackRequestDTO dto) {
        return ResponseEntity.status(201).body(service.adicionarFeedback(id, dto));
    }

    @PutMapping("/{id}/upvote")
    public ResponseEntity<ProjectResponseDTO> upvote(@PathVariable Long id) {
        return ResponseEntity.ok(service.upvote(id));
    }
}