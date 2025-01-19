package org.example.managnentapp.Service;

import org.example.managnentapp.Dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO createUser(Long departmentId , UserDTO userDTO);
    UserDTO updateUser(Long id, UserDTO userDTO);
    void deleteUser(Long id);
    UserDTO getUserById(Long id);
    List<UserDTO> getAllUsers();

}
