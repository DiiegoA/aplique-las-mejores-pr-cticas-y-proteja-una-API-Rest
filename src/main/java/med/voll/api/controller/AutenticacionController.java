package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.usuario.DatosAutenticacionUsuario;
import med.voll.api.domain.usuario.Usuario;
import med.voll.api.domain.usuario.UsuarioRepository;
import med.voll.api.infra.security.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;

    // Constructor para inyectar AuthenticationManager y TokenService
    public AutenticacionController(AuthenticationManager authenticationManager, TokenService tokenService, UsuarioRepository usuarioRepository) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Endpoint para autenticar al usuario y generar un token JWT.
     *
     * @param datosAutenticacionUsuario Datos de autenticación del usuario
     * @return ResponseEntity con el código de estado y el token de autenticación generado
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> autenticarUsuario(@RequestBody @Valid DatosAutenticacionUsuario datosAutenticacionUsuario) {
        // Verificar si el usuario existe
        Usuario usuario = (Usuario) usuarioRepository.findByLogin(datosAutenticacionUsuario.login());
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        // Crear el token de autenticación con el login y la contraseña del usuario
        Authentication authToken = new UsernamePasswordAuthenticationToken(
                datosAutenticacionUsuario.login(),
                datosAutenticacionUsuario.clave()
        );

        // Autenticar al usuario (lanzará una BadCredentialsException si las credenciales son inválidas)
        Authentication usuarioAutenticado = authenticationManager.authenticate(authToken);

        // Generar el JWT token con la información del usuario autenticado
        String jwtToken = tokenService.gerarToken((Usuario) usuarioAutenticado.getPrincipal());

        // Preparar la respuesta con los detalles de la autenticación exitosa
        Map<String, Object> successResponse = new HashMap<>();
        successResponse.put("code", "AUTH_SUCCESS");
        successResponse.put("message", "Autenticación exitosa.");
        successResponse.put("authenticationToken", jwtToken);

        // Retornar la respuesta de éxito con el token generado
        return ResponseEntity.ok(successResponse);
    }

}