package com.api.presupuesto.api_presupuesto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.api.presupuesto.api_presupuesto.exception.PresupuestoNoEncontradoException;

class PresupuestoNoEncontradoExceptionTest {

    @Test
    void constructor_ConIdValido_DeberiaCrearMensajeCorrectamente() {
        Long id = 123L;

        PresupuestoNoEncontradoException exception = new PresupuestoNoEncontradoException(id);

        assertNotNull(exception);
        assertEquals("Presupuesto con ID: 123 no encontrado", exception.getMessage());
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    void constructor_ConIdNull_DeberiaCrearMensajeConNull() {
        Long id = null;

        PresupuestoNoEncontradoException exception = new PresupuestoNoEncontradoException(id);

        assertNotNull(exception);
        assertEquals("Presupuesto con ID: null no encontrado", exception.getMessage());
    }

    @Test
    void constructor_ConIdCero_DeberiaCrearMensajeCorrectamente() {
        Long id = 0L;

        PresupuestoNoEncontradoException exception = new PresupuestoNoEncontradoException(id);

        assertNotNull(exception);
        assertEquals("Presupuesto con ID: 0 no encontrado", exception.getMessage());
    }

    @Test
    void constructor_ConIdNegativo_DeberiaCrearMensajeCorrectamente() {
        Long id = -1L;

        PresupuestoNoEncontradoException exception = new PresupuestoNoEncontradoException(id);

        assertNotNull(exception);
        assertEquals("Presupuesto con ID: -1 no encontrado", exception.getMessage());
    }

    @Test
    void excepcion_DeberiaSerRuntimeException() {
        Long id = 1L;

        PresupuestoNoEncontradoException exception = new PresupuestoNoEncontradoException(id);

        assertTrue(exception instanceof RuntimeException);
        assertNotNull(exception.getMessage());
    }

    @Test
    void excepcion_DeberiaSerLanzable() {
        Long id = 999L;

        assertThrows(PresupuestoNoEncontradoException.class, () -> {
            throw new PresupuestoNoEncontradoException(id);
        });
    }
}