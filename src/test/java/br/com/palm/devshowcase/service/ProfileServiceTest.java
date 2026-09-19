package br.com.palm.devshowcase.service;

import br.com.palm.devshowcase.dto.ProfileRequestDTO;
import br.com.palm.devshowcase.dto.ProfileResponseDTO;
import br.com.palm.devshowcase.model.Profile;
import br.com.palm.devshowcase.repository.ProfileRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {

    @Mock
    private ProfileRepository repository;

    @InjectMocks
    private ProfileService service;

    @Test
    void deveCriarPerfil() {
        ProfileRequestDTO request = new ProfileRequestDTO(
            "Ada Lovelace", "ada@example.com", "Bio", "https://github.com/ada", "https://linkedin.com/in/ada"
        );
        Profile saved = new Profile(request.name(), request.email(), request.bio(), request.githubUrl(), request.linkedinUrl());

        when(repository.existsByEmail(request.email())).thenReturn(false);
        when(repository.save(any(Profile.class))).thenReturn(saved);

        ProfileResponseDTO response = service.criar(request);

        assertEquals(request.name(), response.name());
        assertEquals(request.email(), response.email());
    }

    @Test
    void deveRejeitarEmailDuplicado() {
        ProfileRequestDTO request = new ProfileRequestDTO(
            "Ada Lovelace", "ada@example.com", null, null, null
        );
        when(repository.existsByEmail(request.email())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> service.criar(request));
    }

    @Test
    void deveBuscarPerfilPorId() {
        Profile profile = new Profile("Ada Lovelace", "ada@example.com", null, null, null);
        when(repository.findById(1L)).thenReturn(Optional.of(profile));

        ProfileResponseDTO response = service.buscarPorId(1L);

        assertEquals("Ada Lovelace", response.name());
    }

    @Test
    void deveFalharQuandoPerfilNaoExistir() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> service.buscarPorId(99L));
    }
}