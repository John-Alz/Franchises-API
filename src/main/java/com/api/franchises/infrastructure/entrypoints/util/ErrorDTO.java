package com.api.franchises.infrastructure.entrypoints.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(name = "Error", description = "Detalle de error")
public class ErrorDTO {

    @Schema(example = "FR-4001", description = "Código técnico del error")
    private String code;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Schema(example = "name", description = "Parámetro relacionado")
    private String param;

    @Schema(example = "Nombre inválido", description = "Mensaje técnico")
    private String message;

}
