package br.com.palm.devshowcase.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TechnologyRequestDTO(
    @NotBlank(message = "Nome da tecnologia é obrigatório")
    @Size(max = 50, message = "Nome deve ter no máximo 50 caracteres")
    String name
) {}