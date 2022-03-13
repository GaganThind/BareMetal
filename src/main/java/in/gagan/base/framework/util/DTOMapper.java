package in.gagan.base.framework.util;

import in.gagan.base.framework.dto.user.UserDTO;
import in.gagan.base.framework.dto.user.UserRoleDTO;
import in.gagan.base.framework.entity.user.Role;
import in.gagan.base.framework.entity.user.User;
import org.springframework.beans.BeanUtils;

import java.util.Set;
import java.util.stream.Collectors;

public final class DTOMapper {

    private DTOMapper() { }

    /**
     * Convert UserDTO to User entity object.
     *
     * @param userDTO - Input DTO from client
     * @retun user - Output User entity object
     */
    public static User convertUserDTOToUser(UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        user.setUserRole(convertDTOToRole(userDTO.getUserRole()));

        return user;
    }

    /**
     * Convert Role entity to RoleDTO.
     *
     * @param roles - set of roles
     * @return userRoleDTOs - set of userRoleDTO
     */
    public static Set<UserRoleDTO> convertRoleToDTO(Set<Role> roles) {
        return roles.stream()
                .map(Role::getRoleName)
                .map(UserRoleDTO::new)
                .collect(Collectors.toSet());
    }

    /**
     * Convert RoleDTO to Role entity.
     *
     * @param roles - set of userRoleDTO
     * @return Set<Role> - set of roles
     */
    public static Set<Role> convertDTOToRole(Set<UserRoleDTO> roles) {
        return roles.stream()
                .map(UserRoleDTO::getRoleName)
                .map(Role::new)
                .collect(Collectors.toSet());
    }

    /**
     * Convert User entity object to UserDTO object.
     *
     * @param user - User entity object
     * @return userDTO - Output UserDTO object
     */
    public static UserDTO convertUserToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO,"password", "userRole");
        userDTO.setUserRole(convertRoleToDTO(user.getUserRole()));
        return userDTO;
    }

}
