package com.dosmartie.request;

import com.dosmartie.common.Roles;
import lombok.Data;

@Data
public class UserRequest {
    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private Roles role;
}
