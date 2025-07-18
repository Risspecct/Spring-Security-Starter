package users.rishik.SpringAuthStarter.jwt.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import users.rishik.SpringAuthStarter.jwt.dtos.JwtResponseDto;
import users.rishik.SpringAuthStarter.jwt.dtos.LoginDto;
import users.rishik.SpringAuthStarter.jwt.dtos.RefreshTokenRequestDto;
import users.rishik.SpringAuthStarter.jwt.refresh_tokens.RefreshToken;
import users.rishik.SpringAuthStarter.jwt.refresh_tokens.RefreshTokenService;
import users.rishik.SpringAuthStarter.jwt.security.JwtService;
import users.rishik.SpringAuthStarter.user.standard.UserDto;
import users.rishik.SpringAuthStarter.exceptions.NotFoundException;
import users.rishik.SpringAuthStarter.user.standard.UserService;

@Slf4j
@RestController
public class AuthController {
    private final UserService userService;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;

    AuthController(UserService userService, RefreshTokenService refreshTokenService, JwtService jwtService) {
        this.userService = userService;
        this.refreshTokenService = refreshTokenService;
        this.jwtService = jwtService;
    }

    @Operation(summary = "Register a new user", description = "Register a new user with email, username, password, and role.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input or duplicate email"),
            @ApiResponse(responseCode = "500", description = "Server error during registration")
    })
    @PostMapping("/register")
    public ResponseEntity<?> addUser(@RequestBody @Valid UserDto dto){
        log.info("Register attempt for email {}", dto.getEmail());
        return new ResponseEntity<>(this.userService.addUser(dto), HttpStatus.CREATED);
    }

    @Operation(summary = "User login", description = "Authenticate a user and return a JWT token.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login successful, JWT returned"),
            @ApiResponse(responseCode = "400", description = "Invalid input format"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials"),
            @ApiResponse(responseCode = "500", description = "Server error during login")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDto loginDto){
        log.info("Login attempt for email: {}", loginDto.getEmail());
        return ResponseEntity.ok(this.userService.verify(loginDto));
    }

    // Empty implementation. Can be implemented using blacklist or removing token in the frontend
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Logout user", description = "Logout endpoint (dummy). Can be implemented using token blacklist or client-side removal.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Logout successful (no content)"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - token missing or invalid")
    })
    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Refresh JWT token", description = "Regenerate JWT token before it expires")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Regeneration Successful"),
            @ApiResponse(responseCode = "400", description = "Invalid input format"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials"),
            @ApiResponse(responseCode = "500", description = "Server error during regeneration")
    })
    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(@RequestBody @Valid RefreshTokenRequestDto requestDto){
        return ResponseEntity.ok(refreshTokenService.findByToken(requestDto.getToken())
                .map(this.refreshTokenService::checkExpiry)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String accessToken = this.jwtService.generateToken(user);
                    return JwtResponseDto.builder()
                            .accessToken(accessToken)
                            .token(requestDto.getToken())
                            .build();
                }).orElseThrow(() -> new NotFoundException("Token not found"))
            );
    }
}
