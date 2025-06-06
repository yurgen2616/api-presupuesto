package com.api.presupuesto.api_presupuesto.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class PresupuestoUpdateDTO {

@NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    @NotNull(message = "El monto total es obligatorio")
    @DecimalMin(value = "0.0", inclusive = true, message = "El monto total debe ser mayor o igual a 0")
    private BigDecimal montoTotal;

    @NotNull(message = "El estado es obligatorio")
    @Pattern(regexp = "PENDIENTE|APROBADO|RECHAZADO", message = "El estado debe ser PENDIENTE, APROBADO o RECHAZADO")
    private String estado;
}
