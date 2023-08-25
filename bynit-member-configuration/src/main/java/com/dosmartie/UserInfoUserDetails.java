package com.dosmartie;

import com.dosmartie.entity.CustomerInfo;
import com.dosmartie.entity.MerchantInfo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
@NoArgsConstructor
public class UserInfoUserDetails implements UserDetails {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private String username;
    private String password;
    private List<GrantedAuthority> authorities;

    public UserInfoUserDetails(CustomerInfo userInfo) {
        username = userInfo.getEmail();
        password = userInfo.getPassword();
        authorities = Arrays.stream(userInfo.getRole().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public UserInfoUserDetails(MerchantInfo userInfo) {
        username = userInfo.getEmail();
        password = userInfo.getPassword();
        authorities = Arrays.stream(userInfo.getRole().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
