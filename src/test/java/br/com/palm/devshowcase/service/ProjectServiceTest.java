package br.com.palm.devshowcase.service;

import br.com.palm.devshowcase.dto.ProjectRequestDTO;
import br.com.palm.devshowcase.dto.ProjectResponseDTO;
import br.com.palm.devshowcase.dto.FeedbackRequestDTO;
import br.com.palm.devshowcase.model.Profile;
import br.com.palm.devshowcase.model.Project;
import br.com.palm.devshowcase.model.Technology;
import br.com.palm.devshowcase.repository.ProfileRepository;
import br.com.palm.devshowcase.repository.ProjectRepository;
import br.com.palm.devshowcase.repository.TechnologyRepository;
import br.com.palm.devshowcase.repository.FeedbackRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private TechnologyRepository technologyRepository;

    @Mock
    private FeedbackRepository feedbackRepository;

    @InjectMocks
    private ProjectService service;

    @Test
    void deveCriarProjetoComTecnologias() {
        Profile profile = new Profile("Ada", "ada@example.com", null, null, null);
        Technology technology = new Technology("Java");
        ProjectRequestDTO request = new ProjectRequestDTO(
            "Projeto", "Descricao", "https://example.com/projeto", 1L, Set.of(2L)
        );
        Project saved = new Project();
        saved.setTitle(request.title());
        saved.setDescription(request.description());
        saved.setProjectUrl(request.projectUrl());
        saved.setProfile(profile);
        saved.setTechnologies(Set.of(technology));

        when(profileRepository.findById(1L)).thenReturn(Optional.of(profile));
        when(technologyRepository.findAllById(request.technologyIds())).thenReturn(List.of(technology));
        when(projectRepository.save(any(Project.class))).thenReturn(saved);

        ProjectResponseDTO response = service.criar(request);

        assertEquals("Projeto", response.title());
        assertEquals(1, response.technologies().size());
    }

    @Test
    void deveFalharQuandoPerfilNaoExistir() {
        ProjectRequestDTO request = new ProjectRequestDTO(
            "Projeto", null, "https://example.com/projeto", 99L, null
        );
        when(profileRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> service.criar(request));
    }

    @Test
    void deveFalharQuandoTecnologiaNaoExistir() {
        Profile profile = new Profile("Ada", "ada@example.com", null, null, null);
        ProjectRequestDTO request = new ProjectRequestDTO(
            "Projeto", null, "https://example.com/projeto", 1L, Set.of(2L)
        );
        when(profileRepository.findById(1L)).thenReturn(Optional.of(profile));
        when(technologyRepository.findAllById(request.technologyIds())).thenReturn(List.of());

        assertThrows(NoSuchElementException.class, () -> service.criar(request));
    }

    @Test
    void deveListarProjetos() {
        Project project = new Project();
        project.setTitle("Projeto");
        project.setProjectUrl("https://example.com/projeto");
        project.setProfile(new Profile("Ada", "ada@example.com", null, null, null));
        when(projectRepository.findAll()).thenReturn(List.of(project));

        List<ProjectResponseDTO> response = service.listar();

        assertEquals(1, response.size());
        assertEquals("Projeto", response.get(0).title());
    }

    @Test
    void deveAdicionarFeedbackEAtualizarMedia() {
        Project project = new Project();
        project.setProfile(new Profile("Ada", "ada@example.com", null, null, null));
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(projectRepository.save(any(Project.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ProjectResponseDTO ignored = service.upvote(1L);
        assertEquals(1, ignored.upvotes());

        service.adicionarFeedback(1L, new FeedbackRequestDTO("Caio", "Muito bom", 5, null));
        service.adicionarFeedback(1L, new FeedbackRequestDTO("Bia", "Bom", 3, null));

        assertEquals(4.0, project.getAverageRating());
        assertEquals(2, project.getFeedbacks().size());
    }

    @Test
    void deveIncrementarUpvote() {
        Project project = new Project();
        project.setProfile(new Profile("Ada", "ada@example.com", null, null, null));
        when(projectRepository.findById(2L)).thenReturn(Optional.of(project));
        when(projectRepository.save(any(Project.class))).thenAnswer(invocation -> invocation.getArgument(0));

        service.upvote(2L);
        service.upvote(2L);

        assertEquals(2, project.getUpvotes());
    }
}