package med.voll.api.domain.paciente;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.direccion.DatosDireccion;

public record DatosRespuestaPaciente(
        @NotNull
        Long id,
        String nombre,
        String email,
        String documentoIdentidad,
        String telefono,
        DatosDireccion direccion
) {
        public DatosRespuestaPaciente(Paciente paciente) {
                this(
                        paciente.getId(),
                        paciente.getNombre(),
                        paciente.getEmail(),
                        paciente.getDocumentoIdentidad(),
                        paciente.getTelefono(),
                        new DatosDireccion(
                                paciente.getDireccion().getCalle(),
                                paciente.getDireccion().getDistrito(),
                                paciente.getDireccion().getCiudad(),
                                paciente.getDireccion().getNumero(),
                                paciente.getDireccion().getComplemento()
                        )
                );
        }
}
