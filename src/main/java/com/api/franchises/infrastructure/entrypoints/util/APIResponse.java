package com.api.franchises.infrastructure.entrypoints.util;

import com.api.franchises.infrastructure.entrypoints.dto.FranchiseDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class APIResponse {
    private String code;
    private String message;
    private String identifier;
    private String date;
    private FranchiseDTO data;
    private List<ErrorDTO> errors;
}
