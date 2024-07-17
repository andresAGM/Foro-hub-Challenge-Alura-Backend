package foro.hub.api.domain.topico;

import java.time.LocalDateTime;

public record DatosListadoTopicos(Long id, String titulo, String mensaje, String curso,
                                  Long authorId, LocalDateTime fechaCreacion)
{
    public DatosListadoTopicos(Topico topico)
    {
        this(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getCurso(),
                topico.getAuthor_id().getId(), topico.getFecha_creacion());
    }
}
