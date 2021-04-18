package com.apsi.modules.user.dto;

import com.apsi.modules.user.domain.User;
import lombok.Data;

@Data
public class UserDTO {

    private Long id;

    private String name;

    private String surname;

    private String email;

    private String systemRole;

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.email = user.getEmail();
        this.systemRole = user.getSystemRole().getName();
    }
}
