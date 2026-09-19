package br.com.palm.devshowcase.dto;

import br.com.palm.devshowcase.model.Project;
import java.util.List;

public record ProjectResponseDTO(
    Long id, String title, String description, String projectUrl,
    Long profileId, List<TechnologyResponseDTO> technologies,
    Integer upvotes, Double averageRating
) {
    public static ProjectResponseDTO fromEntity(Project p) {
        return new ProjectResponseDTO(
            p.getId(), p.getTitle(), p.getDescription(), p.getProjectUrl(),
            p.getProfile().getId(),
            p.getTechnologies().stream().map(TechnologyResponseDTO::fromEntity).toList(),
            p.getUpvotes(), p.getAverageRating()
        );
    }
}