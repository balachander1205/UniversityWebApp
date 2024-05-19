package com.api.university.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnalyticsResponse {
    private int totalStudents;
    private int activeStudents;
    private int totalAppointments;
    private int totalUniversities;
}
