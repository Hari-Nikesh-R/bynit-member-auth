package com.dosmartie;

import com.dosmartie.entity.JwtTokenManager;
import org.springframework.data.repository.CrudRepository;

public interface JwtTokenManagerRepository extends CrudRepository<JwtTokenManager, String> {
}
