package com.novigo.demo_park_api.web.Controller;

import com.novigo.demo_park_api.web.Dto.UserCreateDto;
import com.novigo.demo_park_api.web.Dto.UserPasswordDto;
import com.novigo.demo_park_api.web.Dto.UserResponseDto;
import com.novigo.demo_park_api.Entity.User;
import com.novigo.demo_park_api.Services.UserService;
import com.novigo.demo_park_api.web.Exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.novigo.demo_park_api.web.Dto.Mapper.UserMapper;
import java.util.List;


@Tag(name = "Users", description = "This feature provides all necessary operations for user registration.")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;


    @Operation(summary = "Create a new user", description = "Feature for creating a new user",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Feature created successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "409", description = "E-mail user already registered",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Failed to create feature due to invalid input.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    @PostMapping
    public ResponseEntity<UserResponseDto> create(@Valid @RequestBody UserCreateDto userCreateDto) {
        User newUser = userService.save(UserMapper.toUser(userCreateDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDto(newUser));
    }

    @Operation(summary = "Find a user", description = "Feature to find a user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Feature retrieved successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Failed returning a user",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            }
    )

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable Long id) {
        User findUser = userService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(UserMapper.toDto(findUser));
    }


    @Operation(summary = "Update password", description = "Feature to update password",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Password updated successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Password doesn't match",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Failed returning a user",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Invalid or bad formated fields",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @Valid @RequestBody UserPasswordDto passwordDto) {
        User updatePass = userService.updatePassword(id, passwordDto.getActualPass(), passwordDto.getNewPass(), passwordDto.getConfirmPass());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @Operation(summary = "List users", description = "Feature to list all users",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Users returned successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Failed returning users",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAll() {
        List<User> listUsers = userService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(UserMapper.toListDto(listUsers));
    }


}
