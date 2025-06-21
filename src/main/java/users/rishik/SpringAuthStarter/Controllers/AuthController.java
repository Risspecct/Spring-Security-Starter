package users.rishik.SpringAuthStarter.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import users.rishik.SpringAuthStarter.Dtos.LoginDto;
import users.rishik.SpringAuthStarter.Dtos.UserDto;
import users.rishik.SpringAuthStarter.Services.UserService;

@RestController
public class AuthController {
    private final UserService userService;

    AuthController(UserService userService){
        this.userService = userService;
    }

    @Operation(summary = "Register a new user", description = "Register a new user with email, username, password, and role.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input or duplicate email"),
            @ApiResponse(responseCode = "500", description = "Server error during registration")
    })
    @PostMapping("/register")
    public ResponseEntity<?> addUser(@RequestBody @Valid UserDto dto){
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
        return ResponseEntity.ok(this.userService.verify(loginDto));
    }

    // Empty implementation. Can be implemented using blacklist or removing token in the frontend
    @Operation(summary = "Logout user", description = "Logout endpoint (dummy). Can be implemented using token blacklist or client-side removal.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Logout successful (no content)"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - token missing or invalid")
    })
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        return ResponseEntity.noContent().build();
    }
}
