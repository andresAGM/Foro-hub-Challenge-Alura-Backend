package foro.hub.api.domain.topico;

import java.time.LocalDateTime;

public record DatosRespuestaTopicoDTO(
        Long id,
        String titulo,
        String mensaje,
        String curso,
        Long author_id,
        LocalDateTime fechaCreacion
) {
}
