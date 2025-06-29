package users.rishik.SpringAuthStarter.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import users.rishik.SpringAuthStarter.Dtos.RoleUpdateDto;
import users.rishik.SpringAuthStarter.Services.AdminService;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    AdminController(AdminService adminService){
        this.adminService = adminService;
    }

    @Operation(summary = "Get all users", description = "Retrieve a list of all registered users. Accessible only to ADMIN.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied - requires ADMIN role"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - token missing or invalid")
    })
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(){
        return new ResponseEntity<>(this.adminService.getAllUsers(), HttpStatus.OK);
    }

    @Operation(summary = "Delete user by ID", description = "Delete a user by their ID. Accessible only to ADMIN.")
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


    @Operation(summary = "Change role of a user", description = "Admins can change the role of any user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied - requires ADMIN role"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - token missing or invalid")
    })
    @PostMapping("/users/roles")
    public ResponseEntity<?> updateUserRole(@RequestBody @Valid RoleUpdateDto dto){
        this.adminService.updateRole(dto.getUserId(), dto.getRole());
        return ResponseEntity.ok().body("User role updated successfully");
    }
}
