package com.apsi.modules.user;

import com.apsi.global.Identity;
import com.apsi.global.OkResponse;
import com.apsi.modules.user.domain.User;
import com.apsi.modules.user.dto.UserDTO;
import com.apsi.modules.user.query.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/logged")
    public ResponseEntity<?> getLoggedUser() {
        User loggedUser = userRepository.findById(identity.getRawId()).orElseThrow();
        UserDTO response = new UserDTO(loggedUser);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> getUsers() {
        List<User> users = userRepository.findAllByDeletedIsFalse();
        List<UserDTO> response = users.stream().map(UserDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
}
