package com.faithabiola.activity_tracker.repositories;

import com.faithabiola.activity_tracker.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    User getUserByEmailAndPassword(String email, String password);

    User findUserByUuid(String uuid);
}
