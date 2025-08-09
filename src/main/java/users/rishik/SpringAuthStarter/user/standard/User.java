package users.rishik.SpringAuthStarter.user.standard;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import users.rishik.SpringAuthStarter.user.roles.Roles;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    @NotBlank
    private String email;

    @Column(nullable = true)
    private String password;

    @Column(nullable = false)
    @NotNull
    private Roles role;

    @Column
    private String provider = "local";

    @Column
    @NotBlank
    private String providerId = "local";

}
