package med.voll.api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.direccion.DatosDireccion;

public record DatosRegistroMedico(
        @NotBlank(message = "El campo nombre no puede estar vacío")
        String nombre,
        @NotBlank(message = "El campo teléfono no puede estar vacío")
        String telefono,
        @NotBlank(message = "El campo correo electrónico no puede estar vacío")
        @Email(message = "Debe ingresar un correo electrónico válido")
        String email,
        @NotBlank(message = "El campo documento no puede estar vacío")
        @Pattern(regexp = "\\d{8,11}", message = "El documento debe contener entre 8 y 11 dígitos")
        String documento,
        @NotNull(message = "Debe seleccionar una especialidad válida")
        Especialidad especialidad,
        @NotNull(message = "La dirección no puede ser nula")
        @Valid
        DatosDireccion direccion
) {
}
