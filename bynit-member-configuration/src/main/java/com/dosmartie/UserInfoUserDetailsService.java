package com.dosmartie;

import com.dosmartie.entity.CustomerInfo;
import com.dosmartie.entity.MerchantInfo;
import com.dosmartie.helper.CredentialProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserInfoUserDetailsService implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MerchantRepository merchantRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<CustomerInfo> userInfo = customerRepository.findByEmail(username);
        if (userInfo.isPresent()) {
            return validateUser(userInfo.get(), username);
        } else {
            Optional<MerchantInfo> merchantInfo = merchantRepository.findByEmail(username);
            if (merchantInfo.isPresent()) {
                return validateUser(merchantInfo.get(), username);
            }
        }
        throw new UsernameNotFoundException("User not found" + username);
    }

    private synchronized <T extends CredentialProvider> User validateUser(T userInfo, String username) {
        if (userInfo.getEmail().equals(username)) {
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add((GrantedAuthority) userInfo::getEmail);
            return new User(username, userInfo.getPassword(), authorities);
        }
        throw new UsernameNotFoundException("User not found" + username);
    }
}
