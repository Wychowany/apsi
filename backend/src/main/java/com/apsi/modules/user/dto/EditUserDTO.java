package com.apsi.modules.user.dto;

import com.apsi.modules.user.domain.SystemRole;
import lombok.Data;

@Data
public class EditUserDTO {

    private Long id;

    private String name;

    private String surname;

    private String email;

    private SystemRole systemRole;
}
