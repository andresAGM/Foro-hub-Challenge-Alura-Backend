package foro.hub.api.domain.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DatosRegistroTopicoDTO(
        @NotBlank(message = "El titulo no puede estar vacio")
        @Size(max = 200, message = "El titulo no puede tener mas de 200 caracteres")
        String titulo,
        @NotBlank(message = "El mensaje no puede estar vacio")
        @Size(max = 250, message = "El mensaje no puede tener mas de 250 caracteres")
        String mensaje,
        Long authorId,
        @NotBlank(message = "El curso no puede estar vacio")
        String curso
) {
}
