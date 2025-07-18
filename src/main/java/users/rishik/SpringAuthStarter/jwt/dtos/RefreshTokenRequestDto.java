package users.rishik.SpringAuthStarter.jwt.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RefreshTokenRequestDto {
    @NotBlank
    private String token;
}
