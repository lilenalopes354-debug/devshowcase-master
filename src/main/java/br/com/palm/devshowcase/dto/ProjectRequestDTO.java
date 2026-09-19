package br.com.palm.devshowcase.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Set;

public record ProjectRequestDTO(
    @NotBlank(message = "Título é obrigatório")
    @Size(max = 150, message = "Título deve ter no máximo 150 caracteres")
    String title,

    @Size(max = 1000, message = "Descrição deve ter no máximo 1000 caracteres")
    String description,

    @NotBlank(message = "URL do projeto é obrigatória")
    @Pattern(regexp = "https?://.+", message = "projectUrl deve ser uma URL válida")
    String projectUrl,

    @NotNull(message = "profileId é obrigatório")
    Long profileId,

    Set<Long> technologyIds
) {}