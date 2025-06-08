package com.api.presupuesto.api_presupuesto;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.api.presupuesto.api_presupuesto.exception.ManejadorGlobalExcepciones;
import com.api.presupuesto.api_presupuesto.exception.PresupuestoNoEncontradoException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;

class ManejadorGlobalExcepcionesTest {

    private ManejadorGlobalExcepciones manejador;

    @BeforeEach
    void setUp() {
        manejador = new ManejadorGlobalExcepciones();
    }

    @Test
    void handleNotFound_DeberiaRetornarNotFoundConMensaje() {
        Long id = 123L;
        PresupuestoNoEncontradoException exception = new PresupuestoNoEncontradoException(id);

        ResponseEntity<?> response = manejador.handleNotFound(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Presupuesto con ID: 123 no encontrado", response.getBody());
    }

    @SuppressWarnings("null")
    @Test
    void handleValidationErrors_DeberiaRetornarBadRequestConErrores() {
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        
        FieldError fieldError1 = new FieldError("presupuesto", "nombre", "El nombre es obligatorio");
        FieldError fieldError2 = new FieldError("presupuesto", "fecha", "La fecha es obligatoria");
        
        when(exception.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(java.util.List.of(fieldError1, fieldError2));

        ResponseEntity<?> response = manejador.handleValidationErrors(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody() instanceof Map);
        
        @SuppressWarnings("unchecked")
        Map<String, String> errors = (Map<String, String>) response.getBody();
        assertEquals("El nombre es obligatorio", errors.get("nombre"));
        assertEquals("La fecha es obligatoria", errors.get("fecha"));
    }

    @SuppressWarnings("null")
    @Test
    void handleConstraintViolation_DeberiaRetornarBadRequestConViolaciones() {
        ConstraintViolationException exception = mock(ConstraintViolationException.class);
        ConstraintViolation<?> violation = mock(ConstraintViolation.class);
        Path path = mock(Path.class);
        
        when(path.toString()).thenReturn("montoTotal");
        when(violation.getPropertyPath()).thenReturn(path);
        when(violation.getMessage()).thenReturn("El monto debe ser mayor a 0");
        when(exception.getConstraintViolations()).thenReturn(Set.of(violation));

        ResponseEntity<?> response = manejador.handleConstraintViolation(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody() instanceof Map);
        
        @SuppressWarnings("unchecked")
        Map<String, String> errors = (Map<String, String>) response.getBody();
        assertEquals("El monto debe ser mayor a 0", errors.get("montoTotal"));
    }

    @SuppressWarnings("null")
    @Test
    void handleInvalidFormat_DeberiaRetornarBadRequestConDetalleError() {
        HttpMessageNotReadableException exception = mock(HttpMessageNotReadableException.class);
        RuntimeException cause = new RuntimeException("Formato de fecha inválido");
        
        when(exception.getMostSpecificCause()).thenReturn(cause);

        ResponseEntity<?> response = manejador.handleInvalidFormat(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody() instanceof Map);
        
        @SuppressWarnings("unchecked")
        Map<String, String> error = (Map<String, String>) response.getBody();
        assertEquals("Formato de datos inválido", error.get("error"));
        assertEquals("Formato de fecha inválido", error.get("detalle"));
    }

    @Test
    void handleEntityNotFound_DeberiaRetornarNotFound() {
        EntityNotFoundException exception = new EntityNotFoundException("Entidad no encontrada");

        ResponseEntity<?> response = manejador.handleEntityNotFound(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Entidad no encontrada", response.getBody());
    }

    @SuppressWarnings("null")
    @Test
    void handleGeneralError_DeberiaRetornarInternalServerError() {
        Exception exception = new Exception("Error inesperado del sistema");

        ResponseEntity<?> response = manejador.handleGeneralError(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody() instanceof Map);
        
        @SuppressWarnings("unchecked")
        Map<String, String> error = (Map<String, String>) response.getBody();
        assertEquals("Error interno del servidor", error.get("error"));
        assertEquals("Error inesperado del sistema", error.get("detalle"));
    }

    @Test
    void handleGeneralError_ConExcepcionSinMensaje_DeberiaFuncionarCorrectamente() {
        Exception exception = new Exception();

        ResponseEntity<?> response = manejador.handleGeneralError(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody() instanceof Map);
        
        @SuppressWarnings("unchecked")
        Map<String, String> error = (Map<String, String>) response.getBody();
        assertEquals("Error interno del servidor", error.get("error"));
        
        assertTrue(error.containsKey("detalle"));
    }

    @Test
    void todosLosManejadores_DeberianRetornarResponseEntityNoNulo() {

        PresupuestoNoEncontradoException notFoundEx = new PresupuestoNoEncontradoException(1L);
        EntityNotFoundException entityNotFoundEx = new EntityNotFoundException();
        Exception generalEx = new Exception("Test");
        HttpMessageNotReadableException messageEx = mock(HttpMessageNotReadableException.class);
        when(messageEx.getMostSpecificCause()).thenReturn(new RuntimeException("Test"));
        
        assertNotNull(manejador.handleNotFound(notFoundEx));
        assertNotNull(manejador.handleEntityNotFound(entityNotFoundEx));
        assertNotNull(manejador.handleGeneralError(generalEx));
        assertNotNull(manejador.handleInvalidFormat(messageEx));
    }
}