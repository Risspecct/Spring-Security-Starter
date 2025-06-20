package users.rishik.SpringAuthStarter.Controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


    @PostMapping("/register")
    public ResponseEntity<?> addUser(@RequestBody @Valid UserDto dto){
        return new ResponseEntity<>(this.userService.addUser(dto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDto loginDto){
        return ResponseEntity.ok(this.userService.verify(loginDto));
    }

    // Empty implementation. Can be implemented using blacklist or removing token in the frontend
    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        return ResponseEntity.noContent().build();
    }
}
