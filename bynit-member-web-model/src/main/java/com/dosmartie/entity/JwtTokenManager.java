package com.dosmartie.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Data
@AllArgsConstructor
@RedisHash("jwt_token_manager")
public class JwtTokenManager {
    @Id
    private String uuid;
    @Indexed
    private String accessToken;
}
