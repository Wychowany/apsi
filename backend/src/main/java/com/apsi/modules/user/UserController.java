package com.apsi.modules.user;

import com.apsi.global.ErrorResponse;
import com.apsi.global.Identity;
import com.apsi.global.OkResponse;
import com.apsi.modules.user.domain.User;
import com.apsi.modules.user.dto.CreateUserDTO;
import com.apsi.modules.user.dto.EditUserDTO;
import com.apsi.modules.user.dto.UserDTO;
import com.apsi.modules.user.query.UserRepository;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
class UserController {

    @Autowired
    private final Identity identity;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    private static final Logger logger = LogManager.getLogger(UserController.class);

    @GetMapping("/logged")
    public ResponseEntity<?> getLoggedUser() {
        User loggedUser = userRepository.findById(identity.getRawId()).orElseThrow();
        UserDTO response = new UserDTO(loggedUser);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getUsers() {
        List<User> users = userRepository.findAllByDeletedIsFalse();
        List<UserDTO> response = users.stream().map(UserDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> getUser(@RequestParam Long id) {
        User user = userRepository.findById(id).orElseThrow();
        UserDTO response = new UserDTO(user);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody CreateUserDTO createUserDTO) {
        logger.info("User with id {} requested new user registration with data: {}", identity.getRawId(), createUserDTO.toString());
        if (!createUserDTO.getPassword().equals(createUserDTO.getConfirmPassword())) {
            return ResponseEntity.unprocessableEntity().body(
                    new ErrorResponse(
                            HttpStatus.UNPROCESSABLE_ENTITY,
                            "Provided passwords do not match",
                            "Provided passwords do not match"
                    )
            );
        } else {
            User user = User.builder()
                    .name(createUserDTO.getName())
                    .surname(createUserDTO.getSurname())
                    .email(createUserDTO.getEmail())
                    .password(passwordEncoder.encode(createUserDTO.getPassword()))
                    .systemRole(createUserDTO.getSystemRole())
                    .active(true)
                    .accepted(true)
                    .confirmed(true)
                    .build();
            userRepository.save(user);
            return ResponseEntity.ok(new OkResponse());
        }
    }

    @PutMapping
    public ResponseEntity<?> editUser(@RequestBody EditUserDTO editUserDTO) {
        logger.info("User with id {} requested user with id {} update with data: {}", identity.getRawId(), editUserDTO.getId(), editUserDTO.toString());
        User user = userRepository.findById(editUserDTO.getId()).orElseThrow();
        user.setName(editUserDTO.getName());
        user.setSurname(editUserDTO.getSurname());
        user.setEmail(editUserDTO.getEmail());
        user.setSystemRole(editUserDTO.getSystemRole());
        userRepository.save(user);
        return ResponseEntity.ok(new OkResponse());
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestParam Long id) {
        logger.info("User with id {} requested user with id: {} removal", identity.getRawId(), id);
        userRepository.deleteById(id);
        return ResponseEntity.ok(new OkResponse());
    }
}
