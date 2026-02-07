package com.exampleonlineexamination.twd.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.exampleonlineexamination.twd.model.User;
import com.exampleonlineexamination.twd.service.UserService;
import com.exampleonlineexamination.twd.dto.ApiResponse;
import com.exampleonlineexamination.twd.dto.UserRequestDTO;
import com.exampleonlineexamination.twd.dto.UserResponseDTO;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Get all users
     * @return List of all users
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponseDTO>>> getAllUsers() {
        logger.info("Fetching all users");
        try {
            List<User> users = userService.getAllUsers();
            List<UserResponseDTO> response = users.stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponse<>("Success", response, 200));
        } catch (Exception e) {
            logger.error("Error fetching users", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Error fetching users", null, 500));
        }
    }

    /**
     * Get user by ID
     * @param id User ID
     * @return User object
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> getUserById(@PathVariable Integer id) {
        logger.info("Fetching user with ID: {}", id);

        if (id == null || id <= 0) {
            logger.warn("Invalid user ID: {}", id);
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>("Invalid user ID", null, 400));
        }

        try {
            User user = userService.getUserById(id);
            if (user != null) {
                return ResponseEntity.ok(new ApiResponse<>("Success", convertToResponseDTO(user), 200));
            } else {
                logger.warn("User not found with ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("User not found", null, 404));
            }
        } catch (Exception e) {
            logger.error("Error fetching user with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Error fetching user", null, 500));
        }
    }

    /**
     * Get user by email
     * @param email User email
     * @return User object
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> getUserByEmail(@PathVariable String email) {
        logger.info("Fetching user with email: {}", email);

        if (email == null || email.trim().isEmpty()) {
            logger.warn("Empty email provided");
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>("Email cannot be empty", null, 400));
        }

        try {
            User user = userService.getUserByEmail(email);
            if (user != null) {
                return ResponseEntity.ok(new ApiResponse<>("Success", convertToResponseDTO(user), 200));
            } else {
                logger.warn("User not found with email: {}", email);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("User not found", null, 404));
            }
        } catch (Exception e) {
            logger.error("Error fetching user with email: {}", email, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Error fetching user", null, 500));
        }
    }

    /**
     * Get users by role
     * @param role User role (STUDENT/TEACHER/ADMIN)
     * @return List of users with specified role
     */
    @GetMapping("/role/{role}")
    public ResponseEntity<ApiResponse<List<UserResponseDTO>>> getUsersByRole(@PathVariable String role) {
        logger.info("Fetching users with role: {}", role);

        if (role == null || role.trim().isEmpty()) {
            logger.warn("Empty role provided");
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>("Role cannot be empty", null, 400));
        }

        try {
            List<User> users = userService.getUsersByRole(role.toUpperCase());
            List<UserResponseDTO> response = users.stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponse<>("Success", response, 200));
        } catch (Exception e) {
            logger.error("Error fetching users with role: {}", role, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Error fetching users", null, 500));
        }
    }

    /**
     * Create a new user
     * @param userRequest User request DTO
     * @return Created user
     */
    @PostMapping
    public ResponseEntity<ApiResponse<UserResponseDTO>> createUser(@Valid @RequestBody UserRequestDTO userRequest) {
        logger.info("Creating new user with email: {}", userRequest.getEmail());

        try {
            // Check if email already exists
            if (userService.getUserByEmail(userRequest.getEmail()) != null) {
                logger.warn("Email already exists: {}", userRequest.getEmail());
                return ResponseEntity.badRequest()
                        .body(new ApiResponse<>("Email already exists", null, 400));
            }

            User user = convertToEntity(userRequest);
            User created = userService.createUser(user);
            logger.info("User created successfully with ID: {}", created.getUserId());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>("User created successfully", convertToResponseDTO(created), 201));
        } catch (Exception e) {
            logger.error("Error creating user", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Error creating user", null, 500));
        }
    }

    /**
     * Update existing user
     * @param id User ID
     * @param userRequest User request DTO with updated data
     * @return Updated user
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> updateUser(
            @PathVariable Integer id,
            @Valid @RequestBody UserRequestDTO userRequest) {
        logger.info("Updating user with ID: {}", id);

        if (id == null || id <= 0) {
            logger.warn("Invalid user ID: {}", id);
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>("Invalid user ID", null, 400));
        }

        try {
            // Check if user exists
            User existingUser = userService.getUserById(id);
            if (existingUser == null) {
                logger.warn("User not found with ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("User not found", null, 404));
            }

            // Check if new email already exists (and is different from current)
            if (!existingUser.getEmail().equals(userRequest.getEmail())) {
                if (userService.getUserByEmail(userRequest.getEmail()) != null) {
                    logger.warn("Email already exists: {}", userRequest.getEmail());
                    return ResponseEntity.badRequest()
                            .body(new ApiResponse<>("Email already exists", null, 400));
                }
            }

            User user = convertToEntity(userRequest);
            user.setUserId(id);
            User updated = userService.updateUser(id, user);
            logger.info("User updated successfully with ID: {}", id);
            return ResponseEntity.ok(new ApiResponse<>("User updated successfully", convertToResponseDTO(updated), 200));
        } catch (Exception e) {
            logger.error("Error updating user with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Error updating user", null, 500));
        }
    }

    /**
     * Delete user
     * @param id User ID
     * @return Success message
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Integer id) {
        logger.info("Deleting user with ID: {}", id);

        if (id == null || id <= 0) {
            logger.warn("Invalid user ID: {}", id);
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>("Invalid user ID", null, 400));
        }

        try {
            // Check if user exists
            User user = userService.getUserById(id);
            if (user == null) {
                logger.warn("User not found with ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("User not found", null, 404));
            }

            userService.deleteUser(id);
            logger.info("User deleted successfully with ID: {}", id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new ApiResponse<>("User deleted successfully", null, 204));
        } catch (Exception e) {
            logger.error("Error deleting user with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Error deleting user", null, 500));
        }
    }

    /**
     * Convert User entity to UserResponseDTO
     */
    private UserResponseDTO convertToResponseDTO(User user) {
        if (user == null) return null;
        return new UserResponseDTO(
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }

    /**
     * Convert UserRequestDTO to User entity
     */
    private User convertToEntity(UserRequestDTO dto) {
        if (dto == null) return null;
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());
        return user;
    }
}