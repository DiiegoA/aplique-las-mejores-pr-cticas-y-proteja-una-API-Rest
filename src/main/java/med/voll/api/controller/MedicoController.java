package med.voll.api.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.medico.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    private final MedicoRepository medicoRepository;

    public MedicoController(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }


    @GetMapping
    public ResponseEntity<Page<DatosListadoMedico>> listaMedicos(@PageableDefault(size = 2, page = 1, sort = "nombre") Pageable pageable) {
        return ResponseEntity.ok(medicoRepository.findByActivoTrue(pageable).map(DatosListadoMedico::new));
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> registraMedico(@Valid @RequestBody DatosRegistroMedico datosRegistroMedico, UriComponentsBuilder uriComponentsBuilder) {
        // Verificar si el médico ya está registrado
        if (medicoRepository.existsByEmailAndDocumento(datosRegistroMedico.email(), datosRegistroMedico.documento())) {
            throw new IllegalStateException("ERR_DUPLICATE_RECORD"); // Lanza la excepción para manejarla en el global
        }

        // Guardar el médico
        Medico medico = medicoRepository.save(new Medico(datosRegistroMedico));

        // Crear el DTO para la respuesta usando el nuevo constructor
        DatosRespuestaMedico datosRespuestaMedico = new DatosRespuestaMedico(medico);

        // Construir la URL para el header
        URI url = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();

        // Preparar la respuesta
        Map<String, Object> successResponse = new HashMap<>();
        successResponse.put("code", "CREATED");
        successResponse.put("message", "Médico registrado exitosamente.");
        successResponse.put("medico", datosRespuestaMedico);

        // Retornar la respuesta con la URL en el header y el cuerpo con los datos
        return ResponseEntity.created(url).body(successResponse);
    }


    @PutMapping
    @Transactional
    public ResponseEntity<Map<String, Object>> actualizaMedico(@Valid @RequestBody DatosActualizaMedico datosActualizaMedico) {
        // Buscar el médico por ID
        Medico medico = medicoRepository.findById(datosActualizaMedico.id())
                .orElseThrow(() -> new EntityNotFoundException("ERR_RECORD_NOT_FOUND"));

        // Actualizar los datos del médico
        Medico medicoActualizado = medico.actualizarDatos(
                datosActualizaMedico.nombre(),
                datosActualizaMedico.documento(),
                datosActualizaMedico.direccion()
        );
        medicoRepository.save(medicoActualizado);

        // Preparar los datos de respuesta
        Map<String, Object> successResponse = new HashMap<>();
        successResponse.put("code", "UPDATED");
        successResponse.put("message", "Médico actualizado exitosamente.");
        successResponse.put("medico", new DatosRespuestaMedico(medico));

        // Retornar la respuesta con el objeto actualizado y mensaje
        return ResponseEntity.ok(successResponse);
    }


    // DELETE Logico
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Map<String, String>> desactivaMedico(@PathVariable Long id) {
        Medico medico = medicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ERR_RECORD_NOT_FOUND")); // Manejado en el global

        Medico medicoActualizado = medico.desactivarMedico(); // Generar nueva instancia con activo = false
        medicoRepository.save(medicoActualizado); // Guardar el cambio en la base de datos

        // Respuesta de éxito
        return ResponseEntity.noContent().build(); // Código 204 sin cuerpo
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaMedico> retornaDatosMedico(@PathVariable Long id) {
        Medico medico = medicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ERR_RECORD_NOT_FOUND")); // Manejado en el global

        // Crear el DTO para la respuesta
        DatosRespuestaMedico datosRespuestaMedico = new DatosRespuestaMedico(medico);

        // Respuesta de éxito
        return ResponseEntity.ok(datosRespuestaMedico);
    }


    /*@DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Map<String, String>> eliminarMedico(@PathVariable Long id) {
        Medico medico = medicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ERR_MEDICO_NOT_FOUND")); // Manejado en el global

        medicoRepository.delete(medico);

        // Respuesta de éxito
        Map<String, String> successResponse = new HashMap<>();
        successResponse.put("code", "DELETED");
        successResponse.put("message", "Médico eliminado exitosamente.");
        successResponse.put("id", String.valueOf(id));

        return ResponseEntity.ok(successResponse);
    }*/
}