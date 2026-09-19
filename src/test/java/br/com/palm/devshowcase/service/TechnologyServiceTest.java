package br.com.palm.devshowcase.service;

import br.com.palm.devshowcase.dto.TechnologyRequestDTO;
import br.com.palm.devshowcase.dto.TechnologyResponseDTO;
import br.com.palm.devshowcase.model.Technology;
import br.com.palm.devshowcase.repository.TechnologyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TechnologyServiceTest {

    @Mock
    private TechnologyRepository repository;

    @InjectMocks
    private TechnologyService service;

    @Test
    void deveCriarTecnologiaRemovendoEspacosDoNome() {
        TechnologyRequestDTO request = new TechnologyRequestDTO("  Java  ");
        Technology saved = new Technology("Java");
        when(repository.save(any(Technology.class))).thenReturn(saved);

        TechnologyResponseDTO response = service.criar(request);

        assertEquals("Java", response.name());
        verify(repository).save(any(Technology.class));
    }

    @Test
    void deveListarTecnologias() {
        when(repository.findAll()).thenReturn(List.of(new Technology("Java"), new Technology("Spring")));

        List<TechnologyResponseDTO> response = service.buscarTodos();

        assertEquals(2, response.size());
        assertEquals("Spring", response.get(1).name());
    }
}