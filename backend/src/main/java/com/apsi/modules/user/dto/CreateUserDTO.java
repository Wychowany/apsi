package com.apsi.modules.user.dto;

import com.apsi.modules.user.domain.SystemRole;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude = {"password", "confirmPassword"})
public class CreateUserDTO {

    private String name;

    private String surname;

    private String email;

    private String password;

    private String confirmPassword;

    private SystemRole systemRole;
}
