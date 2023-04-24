package ru.moore.archive.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.moore.archive.exceptions.ErrorTemplate;
import ru.moore.archive.models.dtos.response.RoleDTO;
import ru.moore.archive.models.entity.Account;
import ru.moore.archive.repositories.AccountRepository;
import ru.moore.archive.security.JwtProvider;
import ru.moore.archive.security.dto.LoginResponseDTO;
import ru.moore.archive.services.mappers.MapperUtils;

import java.util.HashMap;
import java.util.Map;

@Service("authService")
@RequiredArgsConstructor
public class AuthService {

    private final Map<String, String> refreshStorage = new HashMap<>();

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final AccountRepository accountRepository;
    private final MapperUtils mapperUtils;

    public ResponseEntity<?> loginUser(String login, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, password));
            Account account = accountRepository.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + login));
            String accessToken = jwtProvider.generateAccessToken(account);
            String refreshToken = jwtProvider.generateRefreshToken(account);
            refreshStorage.put(login, null);

            return new ResponseEntity<>(new LoginResponseDTO(accessToken, refreshToken, mapperUtils.mapAll(account.getRoles(), RoleDTO.class)), HttpStatus.OK);
        } catch (BadCredentialsException ex) {
            throw new ErrorTemplate(HttpStatus.NOT_FOUND, "Неверный логин или пароль!");
        }
    }

//    public JwtResponse getAccessToken(@NonNull String refreshToken) {
//        if (jwtProvider.validateRefreshToken(refreshToken)) {
//            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
//            ObjectMapper mapper = new ObjectMapper();
//            Account account = mapper.convertValue(claims.get("user"), Account.class);
//            final String saveRefreshToken = refreshStorage.get(account.getLogin());
//            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
//                final String accessToken = jwtProvider.generateAccessToken(account);
//                return new JwtResponse(accessToken, null);
//            }
//        }
//        return new JwtResponse(null, null);
//    }
//
//    public JwtResponse refresh(@NonNull String refreshToken) {
//        if (jwtProvider.validateRefreshToken(refreshToken)) {
//            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
//            ObjectMapper mapper = new ObjectMapper();
//            Account account = mapper.convertValue(claims.get("user"), Account.class);
//            final String saveRefreshToken = refreshStorage.get(account.getLogin());
//            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
//                final String accessToken = jwtProvider.generateAccessToken(account);
//                final String newRefreshToken = jwtProvider.generateRefreshToken(account);
//                refreshStorage.put(account.getLogin(), newRefreshToken);
//                return new JwtResponse(accessToken, newRefreshToken);
//            }
//        }
//        throw new ErrorTemplate(HttpStatus.BAD_REQUEST, "Невалидный JWT токен");
//    }

}
