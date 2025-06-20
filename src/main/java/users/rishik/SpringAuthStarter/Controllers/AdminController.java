package users.rishik.SpringAuthStarter.Controllers;

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


    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(){
        return new ResponseEntity<>(this.adminService.getAllUsers(), HttpStatus.FOUND);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id){
        this.adminService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}
