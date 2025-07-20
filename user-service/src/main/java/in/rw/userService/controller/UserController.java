package in.rw.userService.controller;

import in.rw.userService.dto.UserDTO;
import in.rw.userService.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService, AuthenticationManager authManager) {
        this.userService = userService;
    }

//    public User

    @GetMapping("/{id}")
    public UserDTO getUserProfile(@PathVariable Long id) {
        log.debug("GET /api/user/{} called", id);
        return userService.getUserProfile(id);
    }

    @GetMapping("/role/{id}")
    public String getUserRole(@PathVariable Long id) {
        log.debug("GET /api/user/role/{} called", id);
        return userService.getUserProfile(id).getRole();
    }

    @GetMapping(value = "/search", params = "name")
    public List<UserDTO> searchByName(@RequestParam String name) {
        log.debug("GET /api/user/search called with parameter name={}", name);
        return userService.searchByName(name);
    }

    @GetMapping(value = "/search", params = "role")
    public List<UserDTO> searchByRole(@RequestParam String role) {
        log.debug("GET /api/user/search called with parameter role={}", role);
        return userService.searchByRole(role);
    }

    @PostMapping("/register")
    public UserDTO registerUser(@RequestBody UserDTO user) {
        log.debug("POST /api/user called for user with name={}", user.getName());
        return  userService.registerUser(user);
    }

    @PutMapping("/update")
    public UserDTO updateUser(@RequestBody UserDTO user) {
        log.debug("PUT /api/user called for user with name={}", user.getName());
        return userService.updateUser(user);
    }
}
