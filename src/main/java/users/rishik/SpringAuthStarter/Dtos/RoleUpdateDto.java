package users.rishik.SpringAuthStarter.Dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import users.rishik.SpringAuthStarter.Entities.Roles;

@Data
@NoArgsConstructor
public class RoleUpdateDto {
    @NotNull
    private int userId;
    @NotNull
    private Roles role;
}
