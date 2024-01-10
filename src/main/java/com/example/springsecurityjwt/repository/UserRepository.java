package com.example.springsecurityjwt.repository;

import com.example.springsecurityjwt.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUsername(String username);
    boolean existsByUsername(String username);
    User findByEmail(String email);
    boolean existsByEmail(String email);
    @Query(value = "SELECT c FROM users WHERE CONCAT(c.email,' ',c.fullname,' ',c.phone) LIKE %?1%",nativeQuery = true)
    Page<User>findAllWithFilter(String keyword, Pageable pageable);

}
