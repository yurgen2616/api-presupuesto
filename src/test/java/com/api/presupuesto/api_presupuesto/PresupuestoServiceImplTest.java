package com.api.presupuesto.api_presupuesto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.api.presupuesto.api_presupuesto.exception.PresupuestoNoEncontradoException;
import com.api.presupuesto.api_presupuesto.model.Presupuesto;
import com.api.presupuesto.api_presupuesto.repository.PresupuestoRepository;
import com.api.presupuesto.api_presupuesto.service.Impl.PresupuestoServiceImpl;

@ExtendWith(MockitoExtension.class)
class PresupuestoServiceImplTest {

    @Mock
    private PresupuestoRepository repository;

    @InjectMocks
    private PresupuestoServiceImpl service;

    private Presupuesto presupuesto;

    @BeforeEach
    void setUp() {
        presupuesto = new Presupuesto();
        presupuesto.setId(1L);
        presupuesto.setNombre("Presupuesto Test");
        presupuesto.setFecha(LocalDate.now());
        presupuesto.setMontoTotal(new BigDecimal("1000.00"));
        presupuesto.setEstado("PENDIENTE");
    }

    @Test
    void save_DeberiaGuardarPresupuesto() {
        when(repository.save(presupuesto)).thenReturn(presupuesto);

        Presupuesto resultado = service.save(presupuesto);

        assertNotNull(resultado);
        assertEquals(presupuesto.getId(), resultado.getId());
        assertEquals(presupuesto.getNombre(), resultado.getNombre());
        verify(repository, times(1)).save(presupuesto);
    }

    @Test
    void findAll_DeberiaRetornarListaDePresupuestos() {
        Presupuesto presupuesto2 = new Presupuesto();
        presupuesto2.setId(2L);
        presupuesto2.setNombre("Presupuesto Test 2");
        presupuesto2.setFecha(LocalDate.now());
        presupuesto2.setMontoTotal(new BigDecimal("2000.00"));
        presupuesto2.setEstado("APROBADO");

        List<Presupuesto> presupuestos = Arrays.asList(presupuesto, presupuesto2);
        when(repository.findAll()).thenReturn(presupuestos);

        List<Presupuesto> resultado = service.findAll();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals(presupuesto.getId(), resultado.get(0).getId());
        assertEquals(presupuesto2.getId(), resultado.get(1).getId());
        verify(repository, times(1)).findAll();
    }

    @Test
    void findById_CuandoExiste_DeberiaRetornarPresupuesto() {
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.of(presupuesto));

        Presupuesto resultado = service.findById(id);

        assertNotNull(resultado);
        assertEquals(presupuesto.getId(), resultado.getId());
        assertEquals(presupuesto.getNombre(), resultado.getNombre());
        verify(repository, times(1)).findById(id);
    }

    @Test
    void findById_CuandoNoExiste_DeberiaLanzarExcepcion() {
        Long id = 999L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        PresupuestoNoEncontradoException exception = assertThrows(
            PresupuestoNoEncontradoException.class,
            () -> service.findById(id)
        );

        assertEquals("Presupuesto con ID: 999 no encontrado", exception.getMessage());
        verify(repository, times(1)).findById(id);
    }

    @Test
    void delete_CuandoExiste_DeberiaEliminarPresupuesto() {
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.of(presupuesto));
        doNothing().when(repository).delete(presupuesto);

        service.delete(id);

        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).delete(presupuesto);
    }

    @Test
    void delete_CuandoNoExiste_DeberiaLanzarExcepcion() {
        Long id = 999L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        PresupuestoNoEncontradoException exception = assertThrows(
            PresupuestoNoEncontradoException.class,
            () -> service.delete(id)
        );

        assertEquals("Presupuesto con ID: 999 no encontrado", exception.getMessage());
        verify(repository, times(1)).findById(id);
        verify(repository, never()).delete(any());
    }
}
