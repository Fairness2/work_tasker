package ru.gb.worktaskmanager.managerauth.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.worktaskmanager.managerauth.converters.UserToDtoConverter;
import ru.gb.worktaskmanager.managerauth.exceptions.AppError;
import ru.gb.worktaskmanager.managerauth.jwt.JwtRequest;
import ru.gb.worktaskmanager.managerauth.jwt.JwtResponse;
import ru.gb.worktaskmanager.managerauth.services.UserService;
import ru.gb.worktaskmanager.managerauth.utils.JwtTokenUtil;

@RestController
@RequiredArgsConstructor
@Tag(name = "Аутентификация", description = "Методы создания токена и аутентификации")
public class AuthController {
    private final UserService userService;
    private final UserToDtoConverter userToDtoConverter;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    @Operation(
            summary = "Создание токена",
            responses = {
                    @ApiResponse(
                            description = "Токен успешно создан", responseCode = "201"
                    )
            }
    )
    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new AppError("CHECK_TOKEN_ERROR", "Некорректный логин или пароль"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
