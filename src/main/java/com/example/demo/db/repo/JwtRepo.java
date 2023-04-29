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

    /**
     * invalidate all jwts of given user by its id
     *
     * @param uid the target user
     */
    @Transactional
    @Modifying
    @Query("update JsonWebToken j set j.valid = false where j.uid = ?1")
    void updateInvalidOldJwt(Integer uid);

    /**
     * Retrieve the latest jwt for a user
     *
     * @param uid of the target user
     * @return An optional jwt object
     */
    @Query("select j from JsonWebToken j where j.uid = ?1 and j.valid = true order by j.timestamp DESC")
    Optional<JsonWebToken> getTokenByUser(Integer uid);

    /**
     * Gets a JWT object by its UUID
     *
     * @param jwt unique id
     * @return An optional jwt object
     */
    @Query("select j from JsonWebToken j where j.jwt = ?1")
    Optional<JsonWebToken> findByUUID(@NonNull UUID jwt);

    /**
     * Deletes all the JWTs of given user
     *
     * @param uid of the target user
     * @return the amount if deleted objects
     */
    @Transactional
    @Modifying
    @Query("delete from JsonWebToken j where j.uid = ?1")
    int deleteByUid(Integer uid);


}
