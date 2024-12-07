package med.voll.api.domain.medico;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.direccion.DatosDireccion;

public record DatosRespuestaMedico(
        @NotNull
        Long id,
        String nombre,
        String email,
        String telefono,
        String especialidad,
        DatosDireccion direccion
) {
        public DatosRespuestaMedico(Medico medico) {
                this(
                        medico.getId(),
                        medico.getNombre(),
                        medico.getEmail(),
                        medico.getTelefono(),
                        medico.getEspecialidad().toValue(),
                        new DatosDireccion(
                                medico.getDireccion().getCalle(),
                                medico.getDireccion().getDistrito(),
                                medico.getDireccion().getCiudad(),
                                medico.getDireccion().getNumero(),
                                medico.getDireccion().getComplemento()
                        )
                );
        }
}
