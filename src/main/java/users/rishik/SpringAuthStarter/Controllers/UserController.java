package users.rishik.SpringAuthStarter.Controllers;

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

    @GetMapping("/")
    public ResponseEntity<?> getUser(@AuthenticationPrincipal UserPrincipal userPrincipal) {
       return new ResponseEntity<>(this.userService.getUser(userPrincipal.getId()), HttpStatus.FOUND);
    }

    @DeleteMapping("/")
    public ResponseEntity<?> deleteUser(@AuthenticationPrincipal UserPrincipal userPrincipal){
        this.userService.deleteUser(userPrincipal.getId());
        return ResponseEntity.ok("User deleted successfully");
    }
}
