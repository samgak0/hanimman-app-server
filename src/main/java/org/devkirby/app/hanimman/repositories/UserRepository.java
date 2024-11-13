package org.devkirby.app.hanimman.repositories;

import java.util.Optional;

import org.devkirby.app.hanimman.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByAndroidId(String androidId);

    Optional<User> findByToken(String token);

    boolean existsByUsername(String username);

    boolean existsByAndroidId(String androidId);
}
