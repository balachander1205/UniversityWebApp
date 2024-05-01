package com.api.university.model;

import com.api.university.entity.UniversityEntity;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Setter
@Getter
public class UniversityResponseModel {
    private List<UniversityEntity> universities;
    private Map representatives;
    private String status;
    private String message;
}
