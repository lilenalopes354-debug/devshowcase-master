package br.com.palm.devshowcase.dto;

import br.com.palm.devshowcase.model.Profile;

public record ProfileResponseDTO(
    Long id, String name, String email, String bio, String githubUrl, String linkedinUrl
) {
    public static ProfileResponseDTO fromEntity(Profile p) {
        return new ProfileResponseDTO(p.getId(), p.getName(), p.getEmail(), p.getBio(), p.getGithubUrl(), p.getLinkedinUrl());
    }
}