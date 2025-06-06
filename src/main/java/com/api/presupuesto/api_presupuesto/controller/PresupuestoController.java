package com.api.presupuesto.api_presupuesto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.presupuesto.api_presupuesto.model.Presupuesto;
import com.api.presupuesto.api_presupuesto.service.PresupuestoService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;




@RestController
@RequestMapping("/api/presupuestos")
public class PresupuestoController {

    @Autowired
    private PresupuestoService service;

    @PostMapping
    public Presupuesto create(@Valid @RequestBody Presupuesto presupuesto){
        return service.save(presupuesto);
    }

    @GetMapping
    public List<Presupuesto> list(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Presupuesto get(@PathVariable Long id){
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public Presupuesto update(@PathVariable Long id, @Valid @RequestBody Presupuesto presupuesto){
        return service.update(id, presupuesto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        service.delete(id);
    }
}
