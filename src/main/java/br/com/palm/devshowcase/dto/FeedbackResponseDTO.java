package br.com.palm.devshowcase.dto;

import br.com.palm.devshowcase.model.Feedback;

public record FeedbackResponseDTO(Long id, String authorName, String comment, Integer rating, Long projectId,
                                  Double projectAverageRating) {
    public static FeedbackResponseDTO fromEntity(Feedback f) {
        return new FeedbackResponseDTO(f.getId(), f.getAuthorName(), f.getComment(), f.getRating(),
            f.getProject().getId(), f.getProject().getAverageRating());
    }
}