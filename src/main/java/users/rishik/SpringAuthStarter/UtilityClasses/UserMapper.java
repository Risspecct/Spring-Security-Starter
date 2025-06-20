package users.rishik.SpringAuthStarter.UtilityClasses;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import users.rishik.SpringAuthStarter.Dtos.UserDto;
import users.rishik.SpringAuthStarter.Entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    User toUsers(UserDto dto);
}
