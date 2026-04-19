package users.rishik.SpringAuthStarter.util;

import users.rishik.SpringAuthStarter.user.roles.Roles;

public interface UserView {
    long getId();
    String getUsername();
    String getEmail();
    Roles getRole();
}
