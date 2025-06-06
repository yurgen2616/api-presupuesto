package com.api.presupuesto.api_presupuesto.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity
@Table(name = "presupuestos")
public class Presupuesto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nombre;

    @NotNull
    private LocalDate fecha;

    @NotNull
    @DecimalMin(value = "0.0",inclusive = true)
    @Column(name = "monto_total")
    private BigDecimal montoTotal;

    @NotNull
    @Pattern(regexp = "PENDIENTE|APROBADO|RECHAZADO")
    private String estado;

}
