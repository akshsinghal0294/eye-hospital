package com.eyehospital.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.eyehospital.enums.MedicineRoute;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicineRequest {

    @NotBlank(message = "Drug name is required")
    private String drugName;

    private String dose;

    private String duration;

    private MedicineRoute  route;
}