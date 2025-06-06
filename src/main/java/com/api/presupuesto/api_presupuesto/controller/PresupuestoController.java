package com.api.presupuesto.api_presupuesto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.presupuesto.api_presupuesto.dto.PresupuestoCreateDTO;
import com.api.presupuesto.api_presupuesto.dto.PresupuestoDTO;
import com.api.presupuesto.api_presupuesto.dto.PresupuestoUpdateDTO;
import com.api.presupuesto.api_presupuesto.mapper.PresupuestoMapper;
import com.api.presupuesto.api_presupuesto.model.Presupuesto;
import com.api.presupuesto.api_presupuesto.service.PresupuestoService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;




@RestController
@RequestMapping("/api/presupuestos")
public class PresupuestoController {

@Autowired
    private PresupuestoService service;

    @Autowired
    private PresupuestoMapper mapper;

    @PostMapping
    public ResponseEntity<PresupuestoDTO> create(@Valid @RequestBody PresupuestoCreateDTO createDto) {
        Presupuesto presupuesto = mapper.toEntity(createDto);
        Presupuesto savedPresupuesto = service.save(presupuesto);
        PresupuestoDTO responseDto = mapper.toDto(savedPresupuesto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<PresupuestoDTO>> list() {
        List<Presupuesto> presupuestos = service.findAll();
        List<PresupuestoDTO> responseDtos = mapper.toDtoList(presupuestos);
        return ResponseEntity.ok(responseDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PresupuestoDTO> get(@PathVariable Long id) {
        Presupuesto presupuesto = service.findById(id);
        PresupuestoDTO responseDto = mapper.toDto(presupuesto);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PresupuestoDTO> update(@PathVariable Long id, 
                                                @Valid @RequestBody PresupuestoUpdateDTO updateDto) {
        Presupuesto existingPresupuesto = service.findById(id);
        mapper.updateEntityFromDto(existingPresupuesto, updateDto);
        Presupuesto updatedPresupuesto = service.save(existingPresupuesto);
        PresupuestoDTO responseDto = mapper.toDto(updatedPresupuesto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}