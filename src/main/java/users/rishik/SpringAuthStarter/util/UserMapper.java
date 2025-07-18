package users.rishik.SpringAuthStarter.util;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import users.rishik.SpringAuthStarter.user.standard.UserDto;
import users.rishik.SpringAuthStarter.user.roles.Roles;
import users.rishik.SpringAuthStarter.user.standard.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    User toUsers(UserDto dto);

    @AfterMapping
    default void setDefaultRole(@MappingTarget User user) {
        user.setRole(Roles.USER);
    }
}
