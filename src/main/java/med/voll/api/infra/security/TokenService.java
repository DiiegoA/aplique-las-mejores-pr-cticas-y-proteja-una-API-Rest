package med.voll.api.infra.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import med.voll.api.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TokenService {

    private final String SECRET;

    // Constructor para inyectar el valor de SECRET
    public TokenService(@Value("${api.security.secret}") String secret) {
        this.SECRET = secret;
    }

    public String getSecretKey() {
        return SECRET;
    }

    // Generar un token con expiración, incluyendo los roles del usuario
    public String gerarToken(Usuario usuario) {
        long expirationTime = 3600000L; // 1 hora (en milisegundos)
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + expirationTime);

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", usuario.getId());

        // Aquí estamos agregando solo el rol del usuario como un valor único
        claims.put("rol", usuario.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority) // Obtener el rol como cadena
                .findFirst() // Obtener solo el primer rol, ya que solo hay uno
                .orElse("ROLE_USER")); // Si no hay rol, asignar ROLE_USER por defecto

        SecretKey secretKey = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .setIssuer("voll med")
                .addClaims(claims)
                .setSubject(usuario.getLogin()) // El subject es el login del usuario
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(secretKey)
                .compact();
    }


    // Validar el token (sin try-catch, lanzando excepciones)
    public boolean validarToken(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

        Jws<Claims> jws = Jwts.parser()
                .setSigningKey(secretKey)
                .requireIssuer("voll med") // Verifica que el issuer sea correcto
                .build()
                .parseClaimsJws(token);  // Esto validará el token

        // Verificar si el "subject" (usuario) es nulo y lanzar una excepción si es el caso
        if (jws.getBody().getSubject() == null) {
            throw new RuntimeException("Verifier invalid");
        }

        return true;
    }

    // Obtener el "subject" del token (nombre de usuario)
    public String getUsernameFromToken(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

        Jws<Claims> jws = Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);

        // Retornar el 'subject' (login) que se guarda en el token
        return jws.getBody().getSubject();
    }
}
