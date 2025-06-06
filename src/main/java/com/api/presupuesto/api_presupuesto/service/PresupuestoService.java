package com.api.presupuesto.api_presupuesto.service;

import java.util.List;
import com.api.presupuesto.api_presupuesto.model.Presupuesto;

public interface PresupuestoService {

    Presupuesto save(Presupuesto presupuesto);
    List<Presupuesto> findAll();
    Presupuesto findById(Long id);
    Presupuesto update(Long id, Presupuesto presupuesto);
    void delete(Long id);

}
