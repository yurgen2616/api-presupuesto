package com.api.presupuesto.api_presupuesto;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.api.presupuesto.api_presupuesto.dto.PresupuestoCreateDTO;
import com.api.presupuesto.api_presupuesto.dto.PresupuestoDTO;
import com.api.presupuesto.api_presupuesto.dto.PresupuestoUpdateDTO;
import com.api.presupuesto.api_presupuesto.mapper.PresupuestoMapper;
import com.api.presupuesto.api_presupuesto.model.Presupuesto;

class PresupuestoMapperTest {

    private PresupuestoMapper mapper;
    private Presupuesto presupuesto;
    private PresupuestoCreateDTO createDTO;
    private PresupuestoUpdateDTO updateDTO;

    @BeforeEach
    void setUp() {
        mapper = new PresupuestoMapper();

        presupuesto = new Presupuesto();
        presupuesto.setId(1L);
        presupuesto.setNombre("Presupuesto Test");
        presupuesto.setFecha(LocalDate.of(2023, 12, 31));
        presupuesto.setMontoTotal(new BigDecimal("1000.00"));
        presupuesto.setEstado("PENDIENTE");

        createDTO = new PresupuestoCreateDTO();
        createDTO.setNombre("Presupuesto Create");
        createDTO.setFecha(LocalDate.of(2023, 11, 30));
        createDTO.setMontoTotal(new BigDecimal("2000.00"));
        createDTO.setEstado("APROBADO");

        updateDTO = new PresupuestoUpdateDTO();
        updateDTO.setNombre("Presupuesto Update");
        updateDTO.setFecha(LocalDate.of(2023, 10, 15));
        updateDTO.setMontoTotal(new BigDecimal("3000.00"));
        updateDTO.setEstado("RECHAZADO");
    }

    @Test
    void toDto_ConPresupuestoValido_DeberiaConvertirCorrectamente() {
        PresupuestoDTO resultado = mapper.toDto(presupuesto);

        assertNotNull(resultado);
        assertEquals(presupuesto.getId(), resultado.getId());
        assertEquals(presupuesto.getNombre(), resultado.getNombre());
        assertEquals(presupuesto.getFecha(), resultado.getFecha());
        assertEquals(presupuesto.getMontoTotal(), resultado.getMontoTotal());
        assertEquals(presupuesto.getEstado(), resultado.getEstado());
    }

    @Test
    void toDto_ConPresupuestoNull_DeberiaRetornarNull() {
        PresupuestoDTO resultado = mapper.toDto(null);

        assertNull(resultado);
    }

    @Test
    void toEntity_ConCreateDTOValido_DeberiaConvertirCorrectamente() {
        Presupuesto resultado = mapper.toEntity(createDTO);

        assertNotNull(resultado);
        assertNull(resultado.getId());
        assertEquals(createDTO.getNombre(), resultado.getNombre());
        assertEquals(createDTO.getFecha(), resultado.getFecha());
        assertEquals(createDTO.getMontoTotal(), resultado.getMontoTotal());
        assertEquals(createDTO.getEstado(), resultado.getEstado());
    }

    @Test
    void toEntity_ConCreateDTONull_DeberiaRetornarNull() {
        Presupuesto resultado = mapper.toEntity(null);

        assertNull(resultado);
    }

    @Test
    void updateEntityFromDto_ConDatosValidos_DeberiaActualizarEntidad() {
        String nombreOriginal = presupuesto.getNombre();
        LocalDate fechaOriginal = presupuesto.getFecha();
        BigDecimal montoOriginal = presupuesto.getMontoTotal();
        String estadoOriginal = presupuesto.getEstado();

        mapper.updateEntityFromDto(presupuesto, updateDTO);

        assertNotNull(presupuesto);
        assertEquals(1L, presupuesto.getId()); 
        assertEquals(updateDTO.getNombre(), presupuesto.getNombre());
        assertEquals(updateDTO.getFecha(), presupuesto.getFecha());
        assertEquals(updateDTO.getMontoTotal(), presupuesto.getMontoTotal());
        assertEquals(updateDTO.getEstado(), presupuesto.getEstado());

        assertNotEquals(nombreOriginal, presupuesto.getNombre());
        assertNotEquals(fechaOriginal, presupuesto.getFecha());
        assertNotEquals(montoOriginal, presupuesto.getMontoTotal());
        assertNotEquals(estadoOriginal, presupuesto.getEstado());
    }

    @Test
    void updateEntityFromDto_ConPresupuestoNull_NoDeberiaLanzarExcepcion() {
        assertDoesNotThrow(() -> mapper.updateEntityFromDto(null, updateDTO));
    }

    @Test
    void updateEntityFromDto_ConUpdateDTONull_NoDeberiaLanzarExcepcion() {
        assertDoesNotThrow(() -> mapper.updateEntityFromDto(presupuesto, null));
    }

    @Test
    void updateEntityFromDto_ConAmbosNull_NoDeberiaLanzarExcepcion() {
        assertDoesNotThrow(() -> mapper.updateEntityFromDto(null, null));
    }

    @Test
    void toDtoList_ConListaValida_DeberiaConvertirTodosLosElementos() {
        Presupuesto presupuesto2 = new Presupuesto();
        presupuesto2.setId(2L);
        presupuesto2.setNombre("Presupuesto Test 2");
        presupuesto2.setFecha(LocalDate.of(2023, 12, 1));
        presupuesto2.setMontoTotal(new BigDecimal("5000.00"));
        presupuesto2.setEstado("APROBADO");

        List<Presupuesto> presupuestos = Arrays.asList(presupuesto, presupuesto2);

        List<PresupuestoDTO> resultado = mapper.toDtoList(presupuestos);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());

        PresupuestoDTO dto1 = resultado.get(0);
        assertEquals(presupuesto.getId(), dto1.getId());
        assertEquals(presupuesto.getNombre(), dto1.getNombre());

        PresupuestoDTO dto2 = resultado.get(1);
        assertEquals(presupuesto2.getId(), dto2.getId());
        assertEquals(presupuesto2.getNombre(), dto2.getNombre());
    }

    @Test
    void toDtoList_ConListaVacia_DeberiaRetornarListaVacia() {
        List<Presupuesto> presupuestosVacios = Arrays.asList();

        List<PresupuestoDTO> resultado = mapper.toDtoList(presupuestosVacios);

        assertNotNull(resultado);
        assertEquals(0, resultado.size());
    }

    @Test
    void toDtoList_ConListaNull_DeberiaRetornarNull() {
        List<PresupuestoDTO> resultado = mapper.toDtoList(null);

        assertNull(resultado);
    }

    @Test
    void toDtoList_ConElementoNullEnLista_DeberiaIncluirNull() {
        List<Presupuesto> presupuestosConNull = Arrays.asList(presupuesto, null);

        List<PresupuestoDTO> resultado = mapper.toDtoList(presupuestosConNull);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertNotNull(resultado.get(0));
        assertNull(resultado.get(1));
    }

    @Test
    void mapeo_DeberiaPreservarPrecisionDecimal() {
        BigDecimal montoConPrecision = new BigDecimal("1234.567890");
        presupuesto.setMontoTotal(montoConPrecision);

        PresupuestoDTO resultado = mapper.toDto(presupuesto);

        assertEquals(montoConPrecision, resultado.getMontoTotal());
        assertEquals(montoConPrecision.scale(), resultado.getMontoTotal().scale());
    }

    @Test
    void mapeo_DeberiaPreservarFechasCorrectamente() {
        LocalDate fechaEspecifica = LocalDate.of(2023, 2, 28);
        presupuesto.setFecha(fechaEspecifica);

        PresupuestoDTO resultado = mapper.toDto(presupuesto);

        assertEquals(fechaEspecifica, resultado.getFecha());
        assertEquals(2023, resultado.getFecha().getYear());
        assertEquals(2, resultado.getFecha().getMonthValue());
        assertEquals(28, resultado.getFecha().getDayOfMonth());
    }
}