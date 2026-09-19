package br.com.palm.devshowcase.service;

import br.com.palm.devshowcase.dto.ProjectRequestDTO;
import br.com.palm.devshowcase.dto.ProjectResponseDTO;
import br.com.palm.devshowcase.dto.FeedbackRequestDTO;
import br.com.palm.devshowcase.dto.FeedbackResponseDTO;
import br.com.palm.devshowcase.model.Feedback;
import br.com.palm.devshowcase.model.Project;
import br.com.palm.devshowcase.model.Profile;
import br.com.palm.devshowcase.repository.ProjectRepository;
import br.com.palm.devshowcase.repository.ProfileRepository;
import br.com.palm.devshowcase.repository.TechnologyRepository;
import br.com.palm.devshowcase.repository.FeedbackRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.List;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProfileRepository profileRepository;
    private final TechnologyRepository technologyRepository;
    private final FeedbackRepository feedbackRepository;

    public ProjectService(ProjectRepository projectRepository, ProfileRepository profileRepository,
                          TechnologyRepository technologyRepository, FeedbackRepository feedbackRepository) {
        this.projectRepository = projectRepository;
        this.profileRepository = profileRepository;
        this.technologyRepository = technologyRepository;
        this.feedbackRepository = feedbackRepository;
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

    @Transactional(readOnly = true)
    public Page<ProjectResponseDTO> listar(String technology, Pageable pageable) {
        Page<Project> projects = technology == null || technology.isBlank()
            ? projectRepository.findAll(pageable)
            : projectRepository.findDistinctByTechnologies_NameIgnoreCase(technology, pageable);
        return projects.map(ProjectResponseDTO::fromEntity);
    }

    @Transactional
    public FeedbackResponseDTO adicionarFeedback(Long projectId, FeedbackRequestDTO dto) {
        Project project = projectRepository.findById(projectId)
            .orElseThrow(() -> new java.util.NoSuchElementException("Projeto não encontrado"));

        Feedback feedback = new Feedback();
        feedback.setAuthorName(dto.authorName());
        feedback.setComment(dto.comment());
        feedback.setRating(dto.rating());
        feedback.setProject(project);
        project.getFeedbacks().add(feedback);
        atualizarMedia(project);
        feedbackRepository.save(feedback);
        projectRepository.save(project);
        return FeedbackResponseDTO.fromEntity(feedback);
    }

    @Transactional
    public ProjectResponseDTO upvote(Long projectId) {
        Project project = projectRepository.findById(projectId)
            .orElseThrow(() -> new java.util.NoSuchElementException("Projeto não encontrado"));
        project.setUpvotes(project.getUpvotes() + 1);
        return ProjectResponseDTO.fromEntity(projectRepository.save(project));
    }

    private void atualizarMedia(Project project) {
        int total = 0;
        int count = 0;
        for (Feedback feedback : project.getFeedbacks()) {
            if (feedback.getRating() != null) {
                total += feedback.getRating();
                count++;
            }
        }
        double average = count == 0 ? 0.0 : (double) total / count;
        project.setAverageRating(Math.round(average * 100.0) / 100.0);
    }
}