package br.com.palm.devshowcase.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ProfileRequestDTO(
    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 120, message = "Nome deve ter no máximo 120 caracteres")
    String name,

    @NotBlank(message = "E-mail é obrigatório")
    @Email(message = "E-mail inválido")
    String email,

    @Size(max = 500, message = "Bio deve ter no máximo 500 caracteres")
    String bio,

    @Pattern(regexp = "https?://.+", message = "githubUrl deve ser uma URL válida")
    String githubUrl,

    @Pattern(regexp = "https?://.+", message = "linkedinUrl deve ser uma URL válida")
    String linkedinUrl
) {}