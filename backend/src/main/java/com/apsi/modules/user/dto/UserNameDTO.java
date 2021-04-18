package com.apsi.modules.user.dto;

import com.apsi.modules.user.domain.User;
import lombok.Data;

@Data
public class UserNameDTO {

    private Long id;

    private String fullName;

    public UserNameDTO(User user) {
        this.id = user.getId();
        this.fullName = user.getFullName();
    }
}
