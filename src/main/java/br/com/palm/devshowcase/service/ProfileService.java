package br.com.palm.devshowcase.service;

import br.com.palm.devshowcase.dto.ProfileRequestDTO;
import br.com.palm.devshowcase.dto.ProfileResponseDTO;
import br.com.palm.devshowcase.model.Profile;
import br.com.palm.devshowcase.repository.ProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfileService {
    private final ProfileRepository repository;

    public ProfileService(ProfileRepository repository) { this.repository = repository; }

    @Transactional
    public ProfileResponseDTO criar(ProfileRequestDTO dto) {
        if (repository.existsByEmail(dto.email())) {
            throw new IllegalArgumentException("Já existe um perfil com este e-mail");
        }
        Profile p = new Profile(dto.name(), dto.email(), dto.bio(), dto.githubUrl(), dto.linkedinUrl());
        return ProfileResponseDTO.fromEntity(repository.save(p));
    }

    @Transactional(readOnly = true)
    public ProfileResponseDTO buscarPorId(Long id) {
        return repository.findById(id)
            .map(ProfileResponseDTO::fromEntity)
            .orElseThrow(() -> new java.util.NoSuchElementException("Perfil não encontrado"));
    }
}