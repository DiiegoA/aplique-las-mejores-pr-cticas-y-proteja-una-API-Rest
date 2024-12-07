package med.voll.api.domain.usuario;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Table(name = "usuarios")
@Entity(name = "Usuario")
@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private final String login;

    @Column(nullable = false)
    private final String clave; // Mantenemos 'clave' como el nombre del campo

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private final RoleEnum rol; // Campo de rol como un enum

    public Usuario(String login, String clave, RoleEnum rol) {
        this.login = login;
        this.clave = clave;
        this.rol = rol;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Devolvemos el rol como un GrantedAuthority, con el prefijo "ROLE_"
        return Collections.singleton(() -> rol.name());
    }

    @Override
    public String getPassword() {
        return clave;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}