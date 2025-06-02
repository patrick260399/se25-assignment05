package de.unibayreuth.se.campuscoffee.api.controller;

import de.unibayreuth.se.campuscoffee.api.dtos.PosDto;
import de.unibayreuth.se.campuscoffee.api.dtos.UserDto;
import de.unibayreuth.se.campuscoffee.api.mapper.UserDtoMapper;
import de.unibayreuth.se.campuscoffee.domain.exceptions.PosNotFoundException;
import de.unibayreuth.se.campuscoffee.domain.exceptions.UserNotFoundException;
import de.unibayreuth.se.campuscoffee.domain.ports.UserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@OpenAPIDefinition(
        info = @Info(
                title = "CampusCoffee",
                version = "0.0.1"
        )
)
@Tag(name = "Users")
@Controller
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    // implement the required endpoints here.

        private final UserService userService;
        private final UserDtoMapper userDtoMapper;

        @GetMapping("")
        public ResponseEntity<List<UserDto>> getAll() {
                return ResponseEntity.ok(
                        userService.getAll().stream()
                                .map(userDtoMapper::fromDomain)
                                .toList()
                );
        }

        @GetMapping("/{id}")
        public ResponseEntity<UserDto> getById(@PathVariable Long id) {
                try {
                        return ResponseEntity.ok(
                                userDtoMapper.fromDomain(userService.getById(id))
                        );
                } catch (UserNotFoundException e) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
                }
        }

        @GetMapping("/filter")
        public ResponseEntity<UserDto> getByLoginName(@RequestParam("loginName") String loginName) {
                try {
                        return ResponseEntity.ok(
                                userDtoMapper.fromDomain(userService.getByLoginName(loginName))
                        );
                } catch (UserNotFoundException e) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
                }
        }

        @PostMapping("")
        public ResponseEntity<UserDto> create(@RequestBody @Valid UserDto userDto) {
                return upsert(userDto);
        }

        @PutMapping("/{id}")
        public ResponseEntity<UserDto> update(@PathVariable Long id, @RequestBody @Valid UserDto userDto) {
                if (!id.equals(userDto.getId())) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User ID in path and body do not match.");
                }

                try {
                        return upsert(userDto);
                } catch (UserNotFoundException e) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
                }
        }

        private ResponseEntity<UserDto> upsert(UserDto userDto) throws UserNotFoundException {
                return ResponseEntity.ok(
                        userDtoMapper.fromDomain(
                                userService.upsert(userDtoMapper.toDomain(userDto))
                        )
                );
        }
}

