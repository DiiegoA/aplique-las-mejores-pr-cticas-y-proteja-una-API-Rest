package med.voll.api.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.medico.DatosActualizaMedico;
import med.voll.api.domain.medico.DatosRegistroMedico;
import med.voll.api.domain.medico.DatosRespuestaMedico;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.paciente.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    private final PacienteRepository pacienteRepository;

    public PacienteController(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoPaciente>> listadoPacientes(@PageableDefault(sort = "nombre") Pageable pageable) {
        return ResponseEntity.ok(pacienteRepository.findByActivoTrue(pageable).map(DatosListadoPaciente::new));
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> registraPaciente(@Valid @RequestBody DatosRegistroPaciente datosRegistroPaciente, UriComponentsBuilder uriComponentsBuilder) {
        // Verificar si el médico ya está registrado
        if (pacienteRepository.existsByEmailAndDocumentoIdentidad(datosRegistroPaciente.email(), datosRegistroPaciente.documentoIdentidad())) {
            throw new IllegalStateException("ERR_DUPLICATE_RECORD"); // Lanza la excepción para manejarla en el global
        }

        // Guardar el médico
        Paciente paciente = pacienteRepository.save(new Paciente(datosRegistroPaciente));

        // Crear el DTO para la respuesta usando el nuevo constructor
        DatosRespuestaPaciente datosRespuestaPaciente = new DatosRespuestaPaciente(paciente);

        // Construir la URL para el header
        URI url = uriComponentsBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();

        // Preparar la respuesta
        Map<String, Object> successResponse = new HashMap<>();
        successResponse.put("code", "CREATED");
        successResponse.put("message", "Paciente registrado exitosamente.");
        successResponse.put("paciente", datosRespuestaPaciente);

        // Retornar la respuesta con la URL en el header y el cuerpo con los datos
        return ResponseEntity.created(url).body(successResponse);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<Map<String, Object>> actualizarPaciente(@Valid @RequestBody DatosActualizaPaciente datosActualizaPaciente) {
        // Buscar el médico por ID
        Paciente paciente = pacienteRepository.findById(datosActualizaPaciente.id())
                .orElseThrow(() -> new EntityNotFoundException("ERR_RECORD_NOT_FOUND"));

        // Actualizar los datos del médico
        Paciente pacienteActualizado = paciente.actualizarDatos(
                datosActualizaPaciente.nombre(),
                datosActualizaPaciente.documentoIdentidad(),
                datosActualizaPaciente.direccion()
        );
        pacienteRepository.save(pacienteActualizado);

        // Preparar los datos de respuesta
        Map<String, Object> successResponse = new HashMap<>();
        successResponse.put("code", "UPDATED");
        successResponse.put("message", "Paciente actualizado exitosamente.");
        successResponse.put("paciente", new DatosRespuestaPaciente(paciente));

        // Retornar la respuesta con el objeto actualizado y mensaje
        return ResponseEntity.ok(successResponse);
    }

    // DELETE Logico
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Map<String, String>> desactivaPaciente(@PathVariable Long id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ERR_RECORD_NOT_FOUND")); // Manejado en el global

        Paciente pacienteActualizado = paciente.desactivarPaciente(); // Generar nueva instancia con activo = false
        pacienteRepository.save(pacienteActualizado); // Guardar el cambio en la base de datos

        // Respuesta de éxito
        return ResponseEntity.noContent().build(); // Código 204 sin cuerpo
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaPaciente> retornaDatosPaciente(@PathVariable Long id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ERR_RECORD_NOT_FOUND")); // Manejado en el global

        // Crear el DTO para la respuesta
        DatosRespuestaPaciente datosRespuestaPaciente = new DatosRespuestaPaciente(paciente);

        // Respuesta de éxito
        return ResponseEntity.ok(datosRespuestaPaciente);
    }
}
