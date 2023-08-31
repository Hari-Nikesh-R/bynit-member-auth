package com.dosmartie.helper;

import com.dosmartie.authconfig.JwtTokenUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@AllArgsConstructor
public class ConnectionHelperData {

    private String merchant;
    private String authId;
}
