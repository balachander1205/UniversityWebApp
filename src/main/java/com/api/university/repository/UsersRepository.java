package com.api.university.repository;

import com.api.university.entity.User;
import com.api.university.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
    @Query("select data from User data where data.email=:username")
    public User getUserDetails(@Param("username") String username);
    @Query("select data from User data where data.email=:username")
    public User findByEmailID(@Param("username") String email);

    @Query("select data from User data where data.email=:email")
    Optional<User> findByEmail(String email);
    Optional<User> findByMobile(String mobile);
}
