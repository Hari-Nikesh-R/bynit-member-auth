package com.dosmartie.request;

import com.dosmartie.common.Roles;
import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
    private Roles roles;
}
