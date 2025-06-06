package com.api.presupuesto.api_presupuesto.exception;

public class PresupuestoNoEncontradoException extends RuntimeException {

    public PresupuestoNoEncontradoException(Long id){
        super("Presupuesto con ID: "+id+" no encontrado");
    }

}
