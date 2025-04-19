package com.api.university.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "representative")
@Getter
@Setter
public class RepresentativeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "repname")
    private String repname;

    @Column(name = "email")
    private String email;

    @Column(name = "phonenumber")
    private String phonenumber;

    @Column(name = "profilepic")
    private String profilepic;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "availability")
    private String availability;

    @Column(name = "zone")
    private String zone;

    @Column(name = "universityid")
    private String universityid;

    @ManyToOne
    private AppointmentsEntity appointments;
}
