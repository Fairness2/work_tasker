package ru.gb.worktaskmanager.managerauth.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.worktaskmanager.managerauth.dtos.UserListDto;
import ru.gb.worktaskmanager.managerauth.services.UserService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "Получение пользователей", description = "Методы работы с пользователями")
public class UsersController {
    private final UserService userService;

    @Operation(
            summary = "Получение списка всех пользователей",
            responses = {
                    @ApiResponse(
                            description = "Список получен", responseCode = "201"
                    )
            }
    )
    @GetMapping("/find-all")
    public UserListDto findAllUsers() {
        return userService.findAllUsers();
    }
}
