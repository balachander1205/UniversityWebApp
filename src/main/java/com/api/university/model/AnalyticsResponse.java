package com.api.university.model;

import lombok.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnalyticsResponse {
    private int totalStudents;
    private int activeStudents;
    private int totalAppointments;
    private int totalUniversities;
    private Map<String, ArrayList> totalAppointmentsByDate;
}
