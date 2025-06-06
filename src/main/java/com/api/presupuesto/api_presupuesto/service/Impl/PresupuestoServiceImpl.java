package com.api.presupuesto.api_presupuesto.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.presupuesto.api_presupuesto.model.Presupuesto;
import com.api.presupuesto.api_presupuesto.repository.PresupuestoRepository;
import com.api.presupuesto.api_presupuesto.service.PresupuestoService;

@Service
public class PresupuestoServiceImpl implements PresupuestoService{

    @Autowired
    private PresupuestoRepository repository;

    @Override
    public Presupuesto save(Presupuesto presupuesto){
        return repository.save(presupuesto);
    }

    @Override
    public List<Presupuesto> findAll(){
        return repository.findAll();
    }

    @Override
    public Presupuesto findById(Long id){
        return repository.findById(id)
            .orElseThrow(()-> new RuntimeException("Presupuesto no encontrado con ID:" + id));
    }

    @Override
    public Presupuesto update(Long id, Presupuesto presupuestoActualizado){
        Presupuesto existing = findById(id);
        existing.setNombre(presupuestoActualizado.getNombre());
        existing.setFecha(presupuestoActualizado.getFecha());
        existing.setMontoTotal(presupuestoActualizado.getMontoTotal());
        existing.setEstado(presupuestoActualizado.getEstado());
        return repository.save(existing);
    }

    @Override
    public void delete(Long id){
        Presupuesto presupuesto = findById(id);
        repository.delete(presupuesto);
    }

}
