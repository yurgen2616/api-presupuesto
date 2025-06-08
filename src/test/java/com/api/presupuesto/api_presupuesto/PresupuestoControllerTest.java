package com.api.presupuesto.api_presupuesto;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.api.presupuesto.api_presupuesto.controller.PresupuestoController;
import com.api.presupuesto.api_presupuesto.dto.PresupuestoCreateDTO;
import com.api.presupuesto.api_presupuesto.dto.PresupuestoDTO;
import com.api.presupuesto.api_presupuesto.dto.PresupuestoUpdateDTO;
import com.api.presupuesto.api_presupuesto.exception.PresupuestoNoEncontradoException;
import com.api.presupuesto.api_presupuesto.mapper.PresupuestoMapper;
import com.api.presupuesto.api_presupuesto.model.Presupuesto;
import com.api.presupuesto.api_presupuesto.service.PresupuestoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@WebMvcTest(PresupuestoController.class)
class PresupuestoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PresupuestoService service;

    @MockBean
    private PresupuestoMapper mapper;

    private ObjectMapper objectMapper;
    private Presupuesto presupuesto;
    private PresupuestoDTO presupuestoDTO;
    private PresupuestoCreateDTO createDTO;
    private PresupuestoUpdateDTO updateDTO;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        presupuesto = new Presupuesto();
        presupuesto.setId(1L);
        presupuesto.setNombre("Presupuesto Test");
        presupuesto.setFecha(LocalDate.of(2023, 12, 31));
        presupuesto.setMontoTotal(new BigDecimal("1000.00"));
        presupuesto.setEstado("PENDIENTE");

        presupuestoDTO = new PresupuestoDTO();
        presupuestoDTO.setId(1L);
        presupuestoDTO.setNombre("Presupuesto Test");
        presupuestoDTO.setFecha(LocalDate.of(2023, 12, 31));
        presupuestoDTO.setMontoTotal(new BigDecimal("1000.00"));
        presupuestoDTO.setEstado("PENDIENTE");

        createDTO = new PresupuestoCreateDTO();
        createDTO.setNombre("Presupuesto Test");
        createDTO.setFecha(LocalDate.of(2023, 12, 31));
        createDTO.setMontoTotal(new BigDecimal("1000.00"));
        createDTO.setEstado("PENDIENTE");

        updateDTO = new PresupuestoUpdateDTO();
        updateDTO.setNombre("Presupuesto Actualizado");
        updateDTO.setFecha(LocalDate.of(2023, 12, 31));
        updateDTO.setMontoTotal(new BigDecimal("1500.00"));
        updateDTO.setEstado("APROBADO");
    }

    @Test
    void create_ConDatosValidos_DeberiaCrearPresupuesto() throws Exception {
        when(mapper.toEntity(any(PresupuestoCreateDTO.class))).thenReturn(presupuesto);
        when(service.save(any(Presupuesto.class))).thenReturn(presupuesto);
        when(mapper.toDto(any(Presupuesto.class))).thenReturn(presupuestoDTO);

        mockMvc.perform(post("/api/presupuestos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Presupuesto Test"))
                .andExpect(jsonPath("$.fecha").value("2023-12-31"))
                .andExpect(jsonPath("$.montoTotal").value(1000.00))
                .andExpect(jsonPath("$.estado").value("PENDIENTE"));

        verify(mapper, times(1)).toEntity(any(PresupuestoCreateDTO.class));
        verify(service, times(1)).save(any(Presupuesto.class));
        verify(mapper, times(1)).toDto(any(Presupuesto.class));
    }

    @Test
    void create_ConDatosInvalidos_DeberiaRetornarBadRequest() throws Exception {
        PresupuestoCreateDTO invalidDTO = new PresupuestoCreateDTO();
        invalidDTO.setNombre("");
        invalidDTO.setFecha(null);
        invalidDTO.setMontoTotal(new BigDecimal("-100"));
        invalidDTO.setEstado("INVALIDO");

        mockMvc.perform(post("/api/presupuestos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void list_DeberiaRetornarListaDePresupuestos() throws Exception {
        List<Presupuesto> presupuestos = Arrays.asList(presupuesto);
        List<PresupuestoDTO> presupuestosDTO = Arrays.asList(presupuestoDTO);

        when(service.findAll()).thenReturn(presupuestos);
        when(mapper.toDtoList(presupuestos)).thenReturn(presupuestosDTO);

        mockMvc.perform(get("/api/presupuestos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nombre").value("Presupuesto Test"));

        verify(service, times(1)).findAll();
        verify(mapper, times(1)).toDtoList(presupuestos);
    }

    @Test
    void get_CuandoExiste_DeberiaRetornarPresupuesto() throws Exception {
        Long id = 1L;
        when(service.findById(id)).thenReturn(presupuesto);
        when(mapper.toDto(presupuesto)).thenReturn(presupuestoDTO);

        mockMvc.perform(get("/api/presupuestos/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Presupuesto Test"));

        verify(service, times(1)).findById(id);
        verify(mapper, times(1)).toDto(presupuesto);
    }

    @Test
    void get_CuandoNoExiste_DeberiaRetornarNotFound() throws Exception {
        Long id = 999L;
        when(service.findById(id)).thenThrow(new PresupuestoNoEncontradoException(id));

        mockMvc.perform(get("/api/presupuestos/{id}", id))
                .andExpect(status().isNotFound());

        verify(service, times(1)).findById(id);
    }

    @Test
    void update_ConDatosValidos_DeberiaActualizarPresupuesto() throws Exception {
        Long id = 1L;
        Presupuesto updatedPresupuesto = new Presupuesto();
        updatedPresupuesto.setId(id);
        updatedPresupuesto.setNombre("Presupuesto Actualizado");
        updatedPresupuesto.setFecha(LocalDate.of(2023, 12, 31));
        updatedPresupuesto.setMontoTotal(new BigDecimal("1500.00"));
        updatedPresupuesto.setEstado("APROBADO");

        PresupuestoDTO updatedDTO = new PresupuestoDTO();
        updatedDTO.setId(id);
        updatedDTO.setNombre("Presupuesto Actualizado");
        updatedDTO.setFecha(LocalDate.of(2023, 12, 31));
        updatedDTO.setMontoTotal(new BigDecimal("1500.00"));
        updatedDTO.setEstado("APROBADO");

        when(service.findById(id)).thenReturn(presupuesto);
        doNothing().when(mapper).updateEntityFromDto(eq(presupuesto), any(PresupuestoUpdateDTO.class));
        when(service.save(presupuesto)).thenReturn(updatedPresupuesto);
        when(mapper.toDto(updatedPresupuesto)).thenReturn(updatedDTO);

        mockMvc.perform(put("/api/presupuestos/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Presupuesto Actualizado"))
                .andExpect(jsonPath("$.montoTotal").value(1500.00))
                .andExpect(jsonPath("$.estado").value("APROBADO"));

        verify(service, times(1)).findById(id);
        verify(mapper, times(1)).updateEntityFromDto(eq(presupuesto), any(PresupuestoUpdateDTO.class));
        verify(service, times(1)).save(presupuesto);
        verify(mapper, times(1)).toDto(updatedPresupuesto);
    }

    @Test
    void delete_CuandoExiste_DeberiaEliminarPresupuesto() throws Exception {
        Long id = 1L;
        doNothing().when(service).delete(id);

        mockMvc.perform(delete("/api/presupuestos/{id}", id))
                .andExpect(status().isNoContent());

        verify(service, times(1)).delete(id);
    }

    @Test
    void delete_CuandoNoExiste_DeberiaRetornarNotFound() throws Exception {
        Long id = 999L;
        doThrow(new PresupuestoNoEncontradoException(id)).when(service).delete(id);

        mockMvc.perform(delete("/api/presupuestos/{id}", id))
                .andExpect(status().isNotFound());

        verify(service, times(1)).delete(id);
    }
}