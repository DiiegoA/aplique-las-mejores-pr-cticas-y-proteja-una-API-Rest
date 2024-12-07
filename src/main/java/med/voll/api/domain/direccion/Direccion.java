package med.voll.api.domain.direccion;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Direccion {

    private final String calle;
    private final String distrito;
    private final String ciudad;
    private final String numero;
    private final String complemento;

    public Direccion(DatosDireccion datosDireccion) {
        this.calle = datosDireccion.calle();
        this.distrito = datosDireccion.distrito();
        this.ciudad = datosDireccion.ciudad();
        this.numero = datosDireccion.numero();
        this.complemento = datosDireccion.complemento();
    }

    public Direccion actualizarDatos(DatosDireccion direccion) {
        return new Direccion(
                direccion.calle(),
                direccion.distrito(),
                direccion.ciudad(),
                direccion.numero(),
                direccion.complemento()
        );
    }
}