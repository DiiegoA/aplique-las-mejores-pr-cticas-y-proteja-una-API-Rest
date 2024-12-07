package med.voll.api.infra.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.usuario.Usuario;
import med.voll.api.domain.usuario.UsuarioRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;

    public JwtAuthenticationFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.replace("Bearer ", "");
            try {
                if (tokenService.validarToken(token)) {
                    String username = tokenService.getUsernameFromToken(token);

                    // Obtener usuario desde la base de datos utilizando el login
                    Usuario usuario = (Usuario) usuarioRepository.findByLogin(username);

                    // Si el usuario es nulo, no manejamos la excepción aquí
                    if (usuario != null) {
                        var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                } else {
                    sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "ERR_INVALID_TOKEN", "Token inválido o expirado");
                    return;
                }
            } catch (MalformedJwtException e) {
                sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "ERR_MALFORMED_TOKEN", "Token mal formado");
                return;
            } catch (ExpiredJwtException e) {
                sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "ERR_TOKEN_EXPIRED", "El token ha expirado");
                return;
            } catch (JwtException e) {
                sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "ERR_INVALID_TOKEN", "Error al validar el token");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private void sendErrorResponse(HttpServletResponse response, int statusCode, String code, String message) throws IOException {
        response.setStatus(statusCode);
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("code", code);
        errorResponse.put("message", message);
        response.setContentType("application/json");
        response.getWriter().write(convertMapToJson(errorResponse));
    }

    private String convertMapToJson(Map<String, String> map) {
        StringBuilder json = new StringBuilder("{");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            json.append("\"").append(entry.getKey()).append("\": \"").append(entry.getValue()).append("\", ");
        }
        if (json.length() > 1) {
            json.setLength(json.length() - 2);
        }
        json.append("}");
        return json.toString();
    }
}