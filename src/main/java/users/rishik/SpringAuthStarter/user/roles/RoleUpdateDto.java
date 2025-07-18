package users.rishik.SpringAuthStarter.user.roles;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoleUpdateDto {
    @NotNull
    private int userId;
    @NotNull
    private Roles role;
}
