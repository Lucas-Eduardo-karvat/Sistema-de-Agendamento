package com.hospital.agendamento_api.security;

import com.hospital.agendamento_api.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Service
public class TokenService {

    // Chave secreta configurada no application.properties ou fallback para desenvolvimento
    @Value("${api.security.token.secret:sua-chave-secreta-super-segura-de-32-caracteres-aqui}")
    private String secret;

    // Tempo de expiração em segundos (24 horas = 86400 segundos)
    private static final long EXPIRATION_IN_SECONDS = 86400;

    /**
     * Gera um token JWT assinado contendo o e-mail, publicId e cargo do usuário.
     */
    public String gerarToken(Usuario usuario) {
        SecretKey key = getSigningKey();

        return Jwts.builder()
                .subject(usuario.getEmail())
                .claim("publicId", usuario.getPublicId().toString())
                .claim("cargo", usuario.getCargo().getNome())
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusSeconds(EXPIRATION_IN_SECONDS)))
                .signWith(key)
                .compact();
    }

    /**
     * Valida o token e retorna o e-mail (subject) contido nele.
     * Retorna null se o token for inválido ou estiver expirado.
     */
    public String validarTokenEObterSubject(String token) {
        try {
            SecretKey key = getSigningKey();

            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            return claims.getSubject();
        } catch (JwtException | IllegalArgumentException e) {
            return null; // Token expirado, adulterado ou inválido
        }
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
}