package com.dosmartie.authconfig;

import com.dosmartie.CustomerRepository;
import com.dosmartie.MerchantNotVerifiedException;
import com.dosmartie.MerchantRepository;
import com.dosmartie.common.Roles;
import com.dosmartie.entity.CustomerInfo;
import com.dosmartie.entity.MerchantInfo;
import com.dosmartie.helper.CredentialProvider;
import com.dosmartie.request.AuthRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.dosmartie.common.Roles.ADMIN;

@Service
@Slf4j
public class UserInfoUserDetailsService implements UserDetailsService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private MerchantRepository merchantRepository;
    @Autowired
    private ObjectMapper mapper;



    @Override
    public UserDetails loadUserByUsername(String request) throws UsernameNotFoundException {
        try {
            AuthRequest authRequest = getAuthRequest(request);
            switch (authRequest.getRole()) {
                case CUSTOMER -> {
                    return fetchUser(authRequest);
                }
                case MERCHANT, ADMIN -> {
                    return fetchMerchant(authRequest);
                }
                case SUPER_ADMIN -> {
                    return fetchAdmin(authRequest);
                }
            }
            throw new UsernameNotFoundException("User not found" + authRequest.getEmail());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private AuthRequest getAuthRequest(String request) throws JsonProcessingException {
        return mapper.readValue(request, AuthRequest.class);
    }

    private User fetchAdmin(AuthRequest authRequest) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (authRequest.getEmail().equals("hari.nikesh.r.cce@sece.ac.in")) {
            List<SimpleGrantedAuthority> authorities = Arrays.stream(authRequest.getRole().toString().split(","))
                    .map(SimpleGrantedAuthority::new).toList();
            return new User(authRequest.getEmail(), bCryptPasswordEncoder.encode("Hari@123"), authorities);
        }
        throw new UsernameNotFoundException("User not found" + authRequest.getEmail());
    }

    private User fetchUser(AuthRequest authRequest) {
        Optional<CustomerInfo> userInfo = customerRepository.findByEmail(authRequest.getEmail());
        if (userInfo.isPresent()) {
            return validateUser(userInfo.get(), authRequest.getEmail());
        }
        throw new UsernameNotFoundException("User not found" + authRequest.getEmail());
    }

    private User fetchMerchant(AuthRequest authRequest) {
        Optional<MerchantInfo> merchantInfo = merchantRepository.findByEmail(authRequest.getEmail());
        if (merchantInfo.isPresent()) {
            if (merchantInfo.get().isVerified() || authRequest.getRole().equals(ADMIN)) {
                log.info("Merchant is Verified, fetching merchant");
                return validateUser(merchantInfo.get(), authRequest.getEmail());
            } else if (authRequest.getRole().equals(Roles.SUPER_ADMIN)) {
                log.info("Fetching Super admin");
                return validateUser(merchantInfo.get(), authRequest.getEmail());
            }
            log.info("Merchant is not Verified");
            throw new MerchantNotVerifiedException();
        }
        throw new UsernameNotFoundException("User not found" + authRequest.getEmail());
    }

    private synchronized <T extends CredentialProvider> User validateUser(T userInfo, String username) {
        if (userInfo.getEmail().equals(username)) {
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add((GrantedAuthority) userInfo::getRole);
            return new User(username, userInfo.getPassword(), authorities);
        }
        throw new UsernameNotFoundException("User not found" + username);
    }
}
