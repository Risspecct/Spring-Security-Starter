package users.rishik.SpringAuthStarter.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import users.rishik.SpringAuthStarter.Security.UserPrincipal;
import users.rishik.SpringAuthStarter.Services.UserService;

@RestController
@PreAuthorize("hasRole('USER')")
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    UserController(UserService userService){
        this.userService = userService;
    }

    @Operation(summary = "Get current user", description = "Get the details of the currently authenticated user.")
    @ApiResponses({
            @ApiResponse(responseCode = "302", description = "User details retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - token missing or invalid"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/")
    public ResponseEntity<?> getUser(@AuthenticationPrincipal UserPrincipal userPrincipal) {
       return new ResponseEntity<>(this.userService.getUser(userPrincipal.getId()), HttpStatus.FOUND);
    }

    @Operation(summary = "Delete current user", description = "Delete the currently authenticated user account.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - token missing or invalid"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/")
    public ResponseEntity<?> deleteUser(@AuthenticationPrincipal UserPrincipal userPrincipal){
        this.userService.deleteUser(userPrincipal.getId());
        return ResponseEntity.ok("User deleted successfully");
    }
}
