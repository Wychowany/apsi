package com.apsi.modules.user.dto;

import com.apsi.modules.user.domain.SystemRole;
import lombok.Data;

@Data
public class CreateUserDTO {

    private String name;

    private String surname;

    private String email;

    private String password;

    private String confirmPassword;

    private SystemRole systemRole;
}
