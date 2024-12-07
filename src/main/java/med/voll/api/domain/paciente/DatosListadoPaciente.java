package med.voll.api.domain.paciente;

public record DatosListadoPaciente(
        Long id,
        String nombre,
        String email,
        String documentoIdentidad,
        String telefono
) {

    public DatosListadoPaciente(Paciente paciente) {
        this(paciente.getId(), paciente.getNombre(), paciente.getEmail(), paciente.getDocumentoIdentidad(),paciente.getTelefono());
    }
}
