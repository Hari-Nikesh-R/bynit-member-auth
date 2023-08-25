package com.dosmartie.response;

import com.dosmartie.common.Roles;
import lombok.Data;

@Data
public class UserResponse {
    private String email;
    private String name;
    private Roles role;
}
