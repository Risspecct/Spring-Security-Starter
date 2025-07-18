package users.rishik.SpringAuthStarter.jwt.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class LoginDto {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
