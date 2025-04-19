package com.api.university.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.json.JSONArray;

import java.util.List;

@Getter
@Setter
@ToString
public class UniversityModel {
    private String universityname;
    private String description;
    private String location;
    private String repname;
    private String position;
    private String admissionintake;
    private String username;
    private String password;
    private String state;
    private String country;
    private String course;
    private String isRecommended;
    private String representatives;
    private String universityID;
    private List<String> images;
}
