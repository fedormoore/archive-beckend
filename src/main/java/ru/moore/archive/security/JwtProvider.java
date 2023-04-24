package ru.moore.archive.security;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import ru.moore.archive.exceptions.ErrorTemplate;
import ru.moore.archive.models.entity.Account;
import ru.moore.archive.security.dto.SecurityAccountDTO;
import ru.moore.archive.security.dto.SecurityDTO;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtProvider {

    private final Logger logger = LoggerFactory.getLogger(ru.moore.archive.security.JwtProvider.class);

    @Value(value ="${jwt.secretAccess}")
    private String jwtAccessSecret;
    @Value(value = "${jwt.secretRefresh}")
    private String jwtRefreshSecret;

    private final ModelMapper modelMapper;

    public String generateAccessToken(Account account) {
        SecurityAccountDTO securityAccountDTO = modelMapper.map(account, SecurityAccountDTO.class);
        SecurityDTO accountDTO = SecurityDTO.builder()
                .account(securityAccountDTO)
                .build();
        final LocalDateTime now = LocalDateTime.now();
        final Instant accessExpirationInstant = now.plusHours(8).atZone(ZoneId.systemDefault()).toInstant();
        final Date accessExpiration = Date.from(accessExpirationInstant);

        return Jwts.builder()
                .claim("user", accountDTO)
                .setIssuedAt(new Date())
                .setExpiration(accessExpiration)
                .signWith(SignatureAlgorithm.HS256, jwtAccessSecret)
                .compact();
    }

    public String generateRefreshToken(Account account) {
        SecurityAccountDTO securityAccountDTO = modelMapper.map(account, SecurityAccountDTO.class);
        SecurityDTO accountDTO = SecurityDTO.builder()
                .account(securityAccountDTO)
                .build();

        final LocalDateTime now = LocalDateTime.now();
        final Instant refreshExpirationInstant = now.plusDays(1).atZone(ZoneId.systemDefault()).toInstant();
        final Date refreshExpiration = Date.from(refreshExpirationInstant);
        return Jwts.builder()
                .claim("user", accountDTO)
                .setExpiration(refreshExpiration)
                .signWith(SignatureAlgorithm.HS512, jwtRefreshSecret)
                .compact();
    }

    public boolean validateToken(@NonNull String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(jwtAccessSecret).parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException expEx) {
//            logger.error("Token expired", expEx);
            throw new ErrorTemplate(HttpStatus.UNAUTHORIZED, "Срок действия токена JWT истек");
        } catch (UnsupportedJwtException unsEx) {
            logger.error("Unsupported jwt", unsEx);
        } catch (MalformedJwtException mjEx) {
//            logger.error("Malformed jwt", mjEx);
            throw new ErrorTemplate(HttpStatus.UNAUTHORIZED, "Не верный JWT");
        } catch (SignatureException sEx) {
            logger.error("Invalid signature", sEx);
        } catch (Exception e) {
            logger.error("invalid token", e);
            throw new ErrorTemplate(HttpStatus.UNAUTHORIZED, "Отсутсвует JWT");
        }
        return false;
    }

    public Authentication getAuthentication(String token) {
        SecurityDTO accountDTO = getUsername(token);

        List<GrantedAuthority> authorities = accountDTO.getAccount().getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(accountDTO, "", authorities);
    }

    public SecurityDTO getUsername(String token) {
        return modelMapper.map(Jwts.parser().setSigningKey(jwtAccessSecret).parseClaimsJws(token).getBody().get("user"), SecurityDTO.class);
    }

}
