package org.devkirby.app.hanimman.repositories;

import java.util.Optional;

import org.devkirby.app.hanimman.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByAndroidId(String androidId);
}
