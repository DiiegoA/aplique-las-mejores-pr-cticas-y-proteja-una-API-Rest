package med.voll.api.domain.direccion;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosDireccion(
        @NotBlank(message = "El campo calle no puede estar vacía")
        String calle,
        @NotBlank(message = "El campo distrito no puede estar vacío")
        String distrito,
        @NotBlank(message = "El campo ciudad no puede estar vacía")
        String ciudad,
        @NotBlank(message = "El campo número no puede estar vacío")
        String numero,
        @NotBlank(message = "El campo complemento no puede estar vacío")
        String complemento
) {
}
