package com.integracao.crmhubspot.repository;

import com.integracao.crmhubspot.model.AuthToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthTokenRepository extends JpaRepository<AuthToken, Long> {

    Optional<AuthToken> findFirstByOrderByIdDesc();

    Optional<AuthToken> findByAccessToken(String accessToken);
}
