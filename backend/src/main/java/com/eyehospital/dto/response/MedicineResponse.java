package com.eyehospital.dto.response;

import com.eyehospital.enums.MedicineRoute;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicineResponse {

    private Long id;

    private String drugName;

    private String dose;

    private String duration;

    private MedicineRoute route;
}