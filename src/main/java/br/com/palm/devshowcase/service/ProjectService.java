package br.com.palm.devshowcase.service;

import br.com.palm.devshowcase.dto.ProjectRequestDTO;
import br.com.palm.devshowcase.dto.ProjectResponseDTO;
import br.com.palm.devshowcase.model.Project;
import br.com.palm.devshowcase.model.Profile;
import br.com.palm.devshowcase.repository.ProjectRepository;
import br.com.palm.devshowcase.repository.ProfileRepository;
import br.com.palm.devshowcase.repository.TechnologyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.List;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProfileRepository profileRepository;
    private final TechnologyRepository technologyRepository;

    public ProjectService(ProjectRepository projectRepository, ProfileRepository profileRepository,
                          TechnologyRepository technologyRepository) {
        this.projectRepository = projectRepository;
        this.profileRepository = profileRepository;
        this.technologyRepository = technologyRepository;
    }

    @Transactional
    public ProjectResponseDTO criar(ProjectRequestDTO dto) {
        Profile profile = profileRepository.findById(dto.profileId())
            .orElseThrow(() -> new java.util.NoSuchElementException("Perfil não encontrado"));

        Project project = new Project();
        project.setTitle(dto.title());
        project.setDescription(dto.description());
        project.setProjectUrl(dto.projectUrl());
        project.setProfile(profile);

        if (dto.technologyIds() != null && !dto.technologyIds().isEmpty()) {
            var technologies = new HashSet<>(technologyRepository.findAllById(dto.technologyIds()));
            if (technologies.size() != dto.technologyIds().size()) {
                throw new java.util.NoSuchElementException("Uma ou mais tecnologias não foram encontradas");
            }
            project.setTechnologies(technologies);
        }

        return ProjectResponseDTO.fromEntity(projectRepository.save(project));
    }

    @Transactional(readOnly = true)
    public List<ProjectResponseDTO> listar() {
        return projectRepository.findAll().stream().map(ProjectResponseDTO::fromEntity).toList();
    }
}