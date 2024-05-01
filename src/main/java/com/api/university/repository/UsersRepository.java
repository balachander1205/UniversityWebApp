package com.api.university.repository;

import com.api.university.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends CrudRepository<UserEntity, Long> {
    @Query("select data from UserEntity data where data.username=:username")
    public UserEntity getUserDetails(@Param("username") String username);
    @Query("select data from UserEntity data where data.username=:username")
    public UserEntity findByEmail(@Param("username") String email);
}
