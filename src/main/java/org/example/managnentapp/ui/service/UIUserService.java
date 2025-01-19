package org.example.managnentapp.ui.service;


import org.example.managnentapp.Dto.UserDTO;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

public class UIUserService {
    private final ApiClient apiClient;

    public UIUserService() {
        this.apiClient = new ApiClient();
    }

    public UserDTO getUserById(Long userId) {
        return apiClient.get("/users/" + userId, UserDTO.class);
    }

    public List<UserDTO> getAllUsers() {
        return apiClient.get("/users", new ParameterizedTypeReference<List<UserDTO>>() {});
    }


    public UserDTO createUser(Long departmentId, UserDTO userDTO) {
        return apiClient.post("/users?departmentId=" + departmentId, userDTO, UserDTO.class);
    }

    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        return apiClient.put("/users/" + userId, userDTO, UserDTO.class);
    }

    public void deleteUser(Long userId) {
        apiClient.delete("/users/" + userId);
    }
    public List<UserDTO> searchUsers(Long departmentId, String query) {
        StringBuilder path = new StringBuilder("/users/search?");
        if (departmentId != null) {
            path.append("departmentId=").append(departmentId);
        }
        if (query != null && !query.isEmpty()) {
            if (departmentId != null) {
                path.append("&");
            }
            path.append("query=").append(query);
        }
        return apiClient.get(path.toString(), new ParameterizedTypeReference<List<UserDTO>>() {});
    }
}


