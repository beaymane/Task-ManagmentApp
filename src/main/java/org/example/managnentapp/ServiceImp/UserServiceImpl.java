package org.example.managnentapp.ServiceImp;

import org.example.managnentapp.Dto.UserDTO;
import org.example.managnentapp.Mapper.UserDTOMapper;
import org.example.managnentapp.Model.Department;
import org.example.managnentapp.Model.User;
import org.example.managnentapp.Repository.DepartmentRepository;
import org.example.managnentapp.Repository.UserRepository;
import org.example.managnentapp.Service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserDTOMapper userDTOMapper;
    private final DepartmentRepository departmentRepository;


    public UserServiceImpl(UserRepository userRepository, UserDTOMapper userDTOMapper, DepartmentRepository departmentRepository) {
        this.userRepository = userRepository;
        this.userDTOMapper = userDTOMapper;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public UserDTO createUser(Long departmentId , UserDTO userDTO) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found"));
        User user = userDTOMapper.toEntity(userDTO);
        user.setDepartment(department);
        User savedUser = userRepository.save(user);
        return userDTOMapper.toDto(savedUser);
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        return null;
    }

    @Override
    public void deleteUser(Long id) {

    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found by id"));
        return userDTOMapper.toDto(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return List.of();
    }
}
