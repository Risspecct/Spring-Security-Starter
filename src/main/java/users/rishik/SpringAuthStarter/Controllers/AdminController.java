package users.rishik.SpringAuthStarter.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import users.rishik.SpringAuthStarter.Services.AdminService;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    AdminController(AdminService adminService){
        this.adminService = adminService;
    }


    @Operation(summary = "Get all users", description = "Retrieve a list of all registered users. Accessible only to ADMINs.")
    @ApiResponses({
            @ApiResponse(responseCode = "302", description = "Users retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied - requires ADMIN role"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - token missing or invalid")
    })
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(){
        return new ResponseEntity<>(this.adminService.getAllUsers(), HttpStatus.FOUND);
    }

    @Operation(summary = "Delete user by ID", description = "Delete a user by their ID. Accessible only to ADMINs.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "403", description = "Access denied - requires ADMIN role"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - token missing or invalid")
    })
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id){
        this.adminService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}
