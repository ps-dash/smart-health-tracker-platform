package in.rw.userService.service;

import in.rw.userService.dto.UserDTO;

import java.util.List;

public interface UserService {
    public UserDTO getUserProfile(Long id);
    public List<UserDTO> searchByName(String name);
    public List<UserDTO> searchByRole(String role);
    public UserDTO registerUser(UserDTO user);
    public UserDTO updateUser(UserDTO user);
}
