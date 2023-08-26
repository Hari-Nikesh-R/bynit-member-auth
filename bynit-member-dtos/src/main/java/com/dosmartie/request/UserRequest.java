package com.dosmartie.request;

import com.dosmartie.common.Roles;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserRequest {
    @Email(message = "Invalid email")
    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private Roles role;
}
