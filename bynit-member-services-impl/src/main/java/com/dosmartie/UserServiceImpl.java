package com.dosmartie;

import com.dosmartie.authconfig.JwtTokenUtil;
import com.dosmartie.helper.ResponseMessage;
import com.dosmartie.request.AuthRequest;
import com.dosmartie.response.AuthResponse;
import com.dosmartie.response.BaseResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.impl.DefaultClaims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
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
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final AuthenticationProvider authenticationProvider;

    private final JwtTokenUtil jwtTokenUtil;

    private final RedisTemplate<String, String> redisTemplate;

    private final UserDetailsService userDetailsService;


    private final ResponseMessage<AuthResponse> responseMessage;

    private final ObjectMapper mapper;

    public UserServiceImpl(AuthenticationProvider authenticationProvider, JwtTokenUtil jwtTokenUtil, UserDetailsService userDetailsService, RedisTemplate<String, String> redisTemplate, ResponseMessage<AuthResponse> responseMessage, ObjectMapper mapper) {
        this.authenticationProvider = authenticationProvider;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.redisTemplate = redisTemplate;
        this.responseMessage = responseMessage;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<BaseResponse<AuthResponse>> signInUser(AuthRequest authRequest) {
        try {
            String auth = mapper.writeValueAsString(authRequest);
            authenticate(auth, authRequest.getPassword());
            System.out.println(auth);
            final UserDetails userDetails = userDetailsService
                    .loadUserByUsername(auth);
            String uuid = generateUUID();
            redisTemplate.opsForValue().set(uuid, jwtTokenUtil.generateToken(userDetails));
            redisTemplate.expire(uuid, 1, TimeUnit.HOURS);
            return ResponseEntity.ok(responseMessage.setSuccessResponse("Authenticated", new AuthResponse(uuid)));
        }
        catch (Exception exception) {
            log.error("Invalid user credential");
            return ResponseEntity.ok(responseMessage.setUnauthorizedResponse("Not Authenticated", exception));
        }
    }

//    @Override
//    public ResponseEntity<BaseResponse<AuthResponse>> logout(String token) {
//        try {
//
//        }
//        catch (Exception exception) {
//            return ResponseEntity.ok(responseMessage.setFailureResponse("Invalid token", exception));
//        }
//    }

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

    private String generateUUID() {
        String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final int[] LENGTHS = { 8, 4, 4, 4, 12 };
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        sb.append("AT-");
        for (int length : LENGTHS) {
            for (int i = 0; i < length; i++) {
                sb.append(characters.charAt(random.nextInt(characters.length())));
            }
            sb.append("-");
        }
        sb.setLength(sb.length() - 1);

        return sb.toString();
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
