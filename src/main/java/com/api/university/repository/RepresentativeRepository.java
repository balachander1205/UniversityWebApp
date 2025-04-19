package com.api.university.repository;

import com.api.university.entity.AppointmentsEntity;
import com.api.university.entity.RepresentativeEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface RepresentativeRepository extends CrudRepository<RepresentativeEntity, Long> {

    @Query(value = "select a from RepresentativeEntity r, AppointmentsEntity a where r.email=:email and r.repname = a.repname")
    public List<AppointmentsEntity> getRepresentativeAppointmentsByEmailID(@Param("email") String email);

    @Query(value = "select r from RepresentativeEntity r")
    public List<RepresentativeEntity> getAllRepresentatives();

    @Transactional
    @Modifying
    @Query(value = "insert into representative (repname, email, phonenumber, profilepic, username, password, universityid, availability, zone) " +
            "values (:repname, :email, :phonenumber, :profilepic, :username, :password, :universityid, :availability, :zone)", nativeQuery = true)
    public void createRepresentative(@Param("repname") String repname, @Param("email") String email, @Param("phonenumber") String phonenumber,
                                     @Param("profilepic") String profilepic, @Param("username") String username, @Param("password") String password,
                                     @Param("universityid") String universityid, @Param("availability") String availability,@Param("zone") String zone);

    @Query(value = "select r from RepresentativeEntity r where r.username=:username")
    public RepresentativeEntity getRepresentativeByUsername(@Param("username") String username);

    @Query(value = "select r from RepresentativeEntity r where r.email=:email")
    public RepresentativeEntity getRepresentativeByEmail(@Param("email") String email);

    @Query(value = "select r from RepresentativeEntity r where r.universityid=:universityid")
    public List<RepresentativeEntity> getRepresentativeByUniversityId(@Param("universityid") String universityid);

    @Transactional
    @Modifying
    @Query(value = "update representative set repname=:repname, email=:email, phonenumber=:phonenumber, username=:username, " +
            "password=:password, universityid=:universityid, availability=:availability, zone=:zone where id=:id" , nativeQuery = true)
    public void updateRepresentative(@Param("repname") String repname, @Param("email") String email, @Param("phonenumber") String phonenumber,
                                     @Param("username") String username, @Param("password") String password,
                                     @Param("universityid") String universityid, @Param("availability") String availability, @Param("id") int id,
                                     @Param("zone") String zone);
}
