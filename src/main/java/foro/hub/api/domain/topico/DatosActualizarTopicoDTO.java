package foro.hub.api.domain.topico;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DatosActualizarTopicoDTO(
        String titulo,
        String mensaje,
        Boolean status,
        Long authorId,
        String curso,
        LocalDateTime fechaCreacion
) {
}
