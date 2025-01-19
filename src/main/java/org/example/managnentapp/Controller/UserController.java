package org.example.managnentapp.Controller;

import org.example.managnentapp.Dto.EmployeeDTO;
import org.example.managnentapp.Dto.UserDTO;
import org.example.managnentapp.ServiceImp.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/{departmentId}")
    public ResponseEntity<UserDTO> createDepartment(
            @RequestBody UserDTO userDTO,
            @PathVariable Long departmentId

    ) {
        UserDTO createdUser = userService.createUser(departmentId , userDTO);
        return ResponseEntity.ok(createdUser);
    }
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId) {
        UserDTO user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }


}
