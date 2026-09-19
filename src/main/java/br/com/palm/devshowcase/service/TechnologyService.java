package br.com.palm.devshowcase.service;

import br.com.palm.devshowcase.dto.TechnologyRequestDTO;
import br.com.palm.devshowcase.dto.TechnologyResponseDTO;
import br.com.palm.devshowcase.model.Technology;
import br.com.palm.devshowcase.repository.TechnologyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class TechnologyService {
    private final TechnologyRepository repository;

    public TechnologyService(TechnologyRepository repository) { this.repository = repository; }

    @Transactional
    public TechnologyResponseDTO criar(TechnologyRequestDTO dto) {
        Technology technology = new Technology(dto.name().trim());
        return TechnologyResponseDTO.fromEntity(repository.save(technology));
    }

    @Transactional(readOnly = true)
    public List<TechnologyResponseDTO> buscarTodos() {
        return repository.findAll().stream().map(TechnologyResponseDTO::fromEntity).toList();
    }
}