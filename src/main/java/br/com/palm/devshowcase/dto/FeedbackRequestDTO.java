package br.com.palm.devshowcase.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record FeedbackRequestDTO(
    @NotBlank(message = "Nome do autor é obrigatório")
    @Size(max = 120, message = "Nome deve ter no máximo 120 caracteres")
    String authorName,

    @NotBlank(message = "Comentário é obrigatório")
    @Size(max = 1000, message = "Comentário deve ter no máximo 1000 caracteres")
    String comment,

    @NotNull(message = "Nota é obrigatória")
    @Min(value = 1, message = "Nota mínima é 1")
    @Max(value = 5, message = "Nota máxima é 5")
    Integer rating,

    Long projectId
) {}