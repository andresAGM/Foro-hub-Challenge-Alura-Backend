package foro.hub.api.domain.topico;

import foro.hub.api.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "topico")
@Entity(name = "Topico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensaje;
    private LocalDateTime fecha_creacion;
    private Boolean status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Usuario author_id;
    private String curso;

    public Topico(String titulo, String mensaje, LocalDateTime fecha_creacion, Boolean status, Usuario author_id, String curso)
    {
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.fecha_creacion = fecha_creacion;
        this.fecha_creacion = LocalDateTime.now();
        this.status = true;
        this.author_id = author_id;
        this.curso = curso;
    }

    public Topico(String titulo, String mensaje, String curso, Long aLong) {
    }

    public void actualizarDatos(DatosActualizarTopicoDTO datos)
    {
        if(datos.titulo() != null)
            this.titulo = datos.titulo();
        if(datos.mensaje() != null)
            this.mensaje = datos.mensaje();
        if(datos.status() != null)
            this.status = datos.status();
        if(datos.curso() != null)
            this.curso = datos.curso();
    }

    public void desactivarTopico() {
        this.status = false;
    }
}
