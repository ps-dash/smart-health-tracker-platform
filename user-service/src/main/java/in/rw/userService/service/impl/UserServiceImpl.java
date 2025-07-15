package in.rw.userService.service.impl;

import in.rw.userService.dto.UserDTO;
import in.rw.userService.model.Role;
import in.rw.userService.model.User;
import in.rw.userService.repository.RoleRepository;
import in.rw.userService.repository.UserRepository;
import in.rw.userService.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static in.rw.userService.LoggingMarkers.AUDIT;
import static java.text.MessageFormat.format;
import static in.rw.userService.LoggingMarkers.ERROR;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        log.debug("UserServiceImpl initialized...");
    }

    public UserDTO getUserProfile(Long id) {
        log.debug("Fetching user profile with id = {}", id);
        return userRepository.findById(id)
                .map(user -> {
                    log.info("Successfully retrieved user profile for id: {}", id);
                    return buildUserDTO(user);
                })
                .orElseThrow(() -> {
                    String errorMessage = format("User not found for id: {0}", id);
                    RuntimeException e = new EntityNotFoundException(errorMessage);
                    log.atError()
                            .setCause(e)
                            .addMarker(ERROR)
                            .log(errorMessage);
                    return e;
                });
    }

    public List<UserDTO> searchByName(String name) {
        log.debug("Fetching users with name {}.", name);
        List<User> users = userRepository.findByNameContainingIgnoreCase(name);
        if (users.isEmpty()) {
            log.info("No users found with name: {}", name);
        } else {
            log.info("Found {} users with name: {}", users.size(), name);
        }
        return buildUserDTOs(users);
    }

    @Override
    public List<UserDTO> searchByRole(String role) {
        log.debug("Fetching users by role {}.", role);
        List<User> users = userRepository.findByRoleName(role.toUpperCase());
        if (users.isEmpty()) {
            log.info("No users found with role: {}", role);
        } else {
            log.info("Found {} users with role: {}", users.size(), role);
        }
        return buildUserDTOs(users);
    }

    public UserDTO registerUser(UserDTO userDTO) {
        log.atDebug()
                .addMarker(AUDIT)
                .log("Registering new user with name: {} and email id: {}",
                        userDTO.getName(), userDTO.getEmail());
        User user = userRepository.save(buildUser(userDTO));
        log.atInfo()
                .addMarker(AUDIT)
                .log("Successfully registered user: name={}, email={}, id={}",
                        user.getName(), user.getEmail(), user.getId());
        return buildUserDTO(user);
    }

    public UserDTO updateUser(UserDTO userDTO) {
        log.atDebug()
                .addMarker(AUDIT)
                .log("Updating user with id: {}", userDTO.getId());
        Long id = userDTO.getId();
        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    String errorMessage = format("User not found with id: {0}", id);
                    RuntimeException e = new EntityNotFoundException(errorMessage);
                    log.atError()
                            .setCause(e)
                            .addMarker(ERROR)
                            .log(errorMessage);
                    return e;
                });
        user = userRepository.save(buildUser(userDTO, user));
        log.atInfo()
                .addMarker(AUDIT)
                .log("Successfully updated user profile with id: {}", user.getId());
        return buildUserDTO(user);
    }

    private List<UserDTO> buildUserDTOs(List<User> userList) {
        log.debug("Mapping list of UserDTO to User entities");
        List<UserDTO> list = new ArrayList<>(userList.size());
        for (User user : userList) list.add(buildUserDTO(user));
        return list;
    }

    private User buildUser(UserDTO userDTO) {
        String roleName = userDTO.getRole();
        Role foundRole = roleRepository.findAll()
                .stream()
                .filter(role -> role.getName().equalsIgnoreCase(roleName))
                .findFirst()
                .orElseThrow(() -> {
                    String errorMessage = format("No such role ({0}) found for the user!", roleName);
                    RuntimeException e = new EntityNotFoundException(errorMessage);
                    log.atError()
                            .setCause(e)
                            .addMarker(ERROR)
                            .log(errorMessage);
                    return e;
                });

        return buildUser(userDTO, User.builder()
                .id(userDTO.getId())
                .role(foundRole)
                .build());
    }

    private User buildUser(UserDTO userDTO, User user) {
        log.debug("Mapping UserDTO to User entity with id = {}", user.getId());
        //name, email, password_hash, mobile, date_of_birth, height_cm, weight_kg, health_issues
        return user.toBuilder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .mobile(userDTO.getMobile())
                .dateOfBirth(userDTO.getDateOfBirth())
                .heightCm(userDTO.getHeight())
                .weightKg(userDTO.getWeight())
                .healthIssues(userDTO.getHealthIssues())
                .build();
    }

    private UserDTO buildUserDTO(User user) {
        log.debug("Mapping User entity to UserDTO with id = {}", user.getId());
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .mobile(user.getMobile())
                .dateOfBirth(user.getDateOfBirth())
                .role(user.getRole().getName())
                .height(user.getHeightCm())
                .weight(user.getWeightKg())
                .healthIssues(user.getHealthIssues())
                .build();
    }
}
