package com.api.university.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "university")
@Getter
@Setter
public class UniversityEntity
{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "universityname")
    private String universityname;

    @Column(name = "description")
    private String description;

    @Column(name = "location")
    private String location;

    @Column(name = "repname")
    private String repname;

    @Column(name = "position")
    private String position;

    @Column(name = "admissionintake")
    private String admissionintake;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "state")
    private String state;

    @Column(name = "countrycode")
    private String countrycode;

    @Column(name = "images")
    private String images;

    @Column(name = "coursetype")
    private String coursetype;

    @Column(name = "isrecommended")
    private String isrecommended;
}