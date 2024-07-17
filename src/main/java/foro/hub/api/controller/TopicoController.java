package foro.hub.api.controller;

import foro.hub.api.domain.topico.*;
import foro.hub.api.domain.usuario.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/topicos")
public class TopicoController
{
    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    @Transactional
    @Operation(summary = "Registra un nuevo topico en la base de datos")
    public ResponseEntity<DatosRespuestaTopicoDTO> crearTopico(@RequestBody @Valid DatosRegistroTopicoDTO topicoDTO,
                                                               UriComponentsBuilder uriComponentsBuilder)
    {
        // usuario
        var usuario = usuarioRepository.findById(topicoDTO.authorId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado para el id: " + topicoDTO.authorId()));
        // Crear el nuevo Topico
        Topico topico = new Topico(
                topicoDTO.titulo(),
                topicoDTO.mensaje(),
                LocalDateTime.now(),
                true,
                usuario,
                topicoDTO.curso()
        );
        // Guardar el nuevo Topico
        topicoRepository.save(topico);
        //datos respuesta
        var datosRespuesta = new DatosRespuestaTopicoDTO(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getCurso(),
                topico.getAuthor_id().getId(),
                topico.getFecha_creacion()
        );

        // Crear la URI de respuesta
        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        // Retornar el código 201 Created
        return ResponseEntity.created(url).body(datosRespuesta);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene un topico por su id")
    public ResponseEntity<DatosRespuestaTopicoDTO> obtenerTopico(@PathVariable Long id)
    {
        // Buscar el topico por su id
        var topico = topicoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Topico no encontrado para el id: " + id));
        //datos respuesta
        var datosRespuesta = new DatosRespuestaTopicoDTO(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getCurso(),
                topico.getAuthor_id().getId(),
                topico.getFecha_creacion()
        );
        // Retornar el topico encontrado
        return ResponseEntity.ok(datosRespuesta);
    }

    @GetMapping
    @Operation(summary = "Obtiene todos los topicos")
    public ResponseEntity<Page<DatosListadoTopicos>> listadoTopicos(@PageableDefault(size = 2) Pageable paginacion)
    {
        return ResponseEntity.ok(topicoRepository.findByStatusTrue(paginacion).map(DatosListadoTopicos::new));
    }

    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Actualiza un topico por su id")
    public ResponseEntity<DatosRespuestaTopicoDTO> actualizarTopico(@PathVariable Long id,
                                                                    @RequestBody @Valid DatosActualizarTopicoDTO datos)
    {
        // Buscar el topico por su id
        var topico = topicoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Topico no encontrado para el id: " + id));
        // Actualizar los datos del topico
        topico.actualizarDatos(datos);
        //datos respuesta
        var datosRespuesta = new DatosRespuestaTopicoDTO(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getCurso(),
                topico.getAuthor_id().getId(),
                topico.getFecha_creacion()
        );
        // Retornar el topico actualizado
        return ResponseEntity.ok(datosRespuesta);
    }

    //eliminacion logica d eun topico
    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Elimina un topico por su id")
    public ResponseEntity<DatosRespuestaTopicoDTO> eliminarTopico(@PathVariable Long id)
    {
        // Buscar el topico por su id
        var topico = topicoRepository.getReferenceById(id);

        // Desactivar el topico
        topico.desactivarTopico();

        // Retornar el topico desactivado
        return ResponseEntity.noContent().build();
    }
}
