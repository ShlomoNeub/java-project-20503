package com.example.demo.db.repo;

import com.example.demo.db.entities.JsonWebToken;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface JwtRepo extends CrudRepository<JsonWebToken, Integer> {
    @Transactional
    @Modifying
    @Query("update JsonWebToken j set j.valid = false where j.uid = ?1")
    void updateInvalidOldJwt(Integer uid);

    @Query("select j from JsonWebToken j where j.uid = ?1 and j.valid = true order by j.timestamp DESC")
    Optional<JsonWebToken> getTokenByUser(Integer uid);

    @Query("select j from JsonWebToken j where j.jwt = ?1")
    Optional<JsonWebToken> findByUUID(@NonNull UUID jwt);

    @Transactional
    @Modifying
    @Query("delete from JsonWebToken j where j.uid = ?1")
    int deleteByUid(Integer uid);


}