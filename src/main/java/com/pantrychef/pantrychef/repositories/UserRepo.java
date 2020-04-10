package com.pantrychef.pantrychef.repositories;

import com.pantrychef.pantrychef.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
    @Query("SELECT u FROM User u WHERE u.id = ?1")
    User getUserById(Long id);
}
