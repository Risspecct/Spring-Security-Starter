package users.rishik.SpringAuthStarter.Dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import users.rishik.SpringAuthStarter.Entities.Roles;

@Data
@NoArgsConstructor
public class UserDto {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
