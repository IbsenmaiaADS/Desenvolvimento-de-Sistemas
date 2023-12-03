// package com.controletotal.controletotal.infra.security;

// import com.auth0.jwt.JWT;
// import com.auth0.jwt.algorithms.Algorithm;
// import com.auth0.jwt.exceptions.JWTCreationException;
// import com.auth0.jwt.exceptions.JWTVerificationException;
// import com.controletotal.controletotal.entity.Usuario;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.stereotype.Service;

// import java.time.Instant;
// import java.time.LocalDateTime;
// import java.time.ZoneOffset;

// @Service
// public class TokenService {
//     @Value("${api.security.token.secret}")
//     private String secret;

//     public String gerarToken(Usuario usuario){
//         try{
//             Algorithm algorithm = Algorithm.HMAC256(secret);
//             String token = JWT.create()
//                     .withIssuer("controletotal")
//                     .withSubject(usuario.getEmail())
//                     .withExpiresAt(genExpirationDate())
//                     .sign(algorithm);
//             return token;
//         } catch (JWTCreationException exception) {
//             throw new RuntimeException("Erro ao gerar token", exception);
//         }
//     }

//     public String validarToken(String token){
//         try {
//             Algorithm algorithm = Algorithm.HMAC256(secret);
//             return JWT.require(algorithm)
//                     .withIssuer("controletotal")
//                     .build()
//                     .verify(token)
//                     .getSubject();
//         } catch (JWTVerificationException exception){
//             throw new RuntimeException("Erro ao validar token", exception);
//         }
//     }

//     private Instant genExpirationDate(){
//         return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
//     }
// }
