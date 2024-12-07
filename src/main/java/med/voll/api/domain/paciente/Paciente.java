package med.voll.api.domain.paciente;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.direccion.Direccion;


@Table(name = "pacientes")
@Entity(name = "Paciente")
@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private final String nombre;

    @Column(unique = true, nullable = false)
    private final String email;

    @Column(unique = true, nullable = false)
    private final String documentoIdentidad;

    @Column(nullable = false)
    private final String telefono;

    @Embedded
    private final Direccion direccion;

    private final Boolean activo;

    public Paciente(DatosRegistroPaciente datosRegistroPaciente) {
        this(null,
        datosRegistroPaciente.nombre(),
        datosRegistroPaciente.email(),
        datosRegistroPaciente.documentoIdentidad(),
        datosRegistroPaciente.telefono(),
        new Direccion(datosRegistroPaciente.direccion()),
        true
        );
    }

    public Paciente actualizarDatos(String nombre, String documentoIdentidad, DatosDireccion direccion) {
        return new Paciente(
                this.id,
                nombre != null ? nombre : this.nombre, // Mantén el valor actual si es null
                this.email,    // Email no se actualiza en este método
                documentoIdentidad != null ? documentoIdentidad : this.documentoIdentidad, // Mantén el valor actual si es null
                this.telefono, // Teléfono no se actualiza en este método
                direccion != null ? this.direccion.actualizarDatos(direccion) : this.direccion, // Actualiza la dirección si no es null
                this.activo
        );
    }

    // Método para desactivar al médico
    public Paciente desactivarPaciente() {
        return new Paciente(
                this.id,
                this.nombre,
                this.email,
                this.documentoIdentidad,
                this.telefono,
                this.direccion,
                false // Cambiar activo a false
        );
    }
}
