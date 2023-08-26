package com.dosmartie;

import com.dosmartie.entity.JwtTokenManager;
import com.dosmartie.helper.ResponseMessage;
import com.dosmartie.request.AuthRequest;
import com.dosmartie.response.AuthResponse;
import com.dosmartie.response.BaseResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.impl.DefaultClaims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenManagerRepository jwtTokenManagerRepository;
    @Autowired
    private ResponseMessage<AuthResponse> responseMessage;
    @Autowired
    private ObjectMapper mapper;

    @Override
    public ResponseEntity<BaseResponse<AuthResponse>> signInUser(AuthRequest authRequest) {
        try {
            String auth = mapper.writeValueAsString(authRequest);
            authenticate(auth, authRequest.getPassword());
            System.out.println(auth);
            final UserDetails userDetails = userDetailsService
                    .loadUserByUsername(auth);
            final String token = jwtTokenUtil.generateToken(userDetails);
            return ResponseEntity.ok(responseMessage.setSuccessResponse("Authenticated", new AuthResponse(token)));
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.ok(responseMessage.setFailureResponse("Not Authenticated", exception));
        }
    }

    @Override
    public ResponseEntity<BaseResponse<AuthResponse>> getRefreshToken(HttpServletRequest request) {
        try {
            DefaultClaims claims = (io.jsonwebtoken.impl.DefaultClaims) request.getAttribute("claims");
            Map<String, Object> expectedMap = getMapFromIoJsonwebtokenClaims(claims);
            String token = jwtTokenUtil.doGenerateRefreshToken(expectedMap, expectedMap.get("sub").toString());

            return ResponseEntity.ok(responseMessage.setSuccessResponse("Fetched token via refresh token code",new AuthResponse(token)));
        }
        catch (Exception exception) {
            return ResponseEntity.ok(responseMessage.setFailureResponse("Unable to fetch token code", exception));

        }
    }

    public Map<String, Object> getMapFromIoJsonwebtokenClaims(DefaultClaims claims) {
        return new HashMap<>(claims);
    }


    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
