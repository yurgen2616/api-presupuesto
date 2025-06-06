package com.api.presupuesto.api_presupuesto.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.api.presupuesto.api_presupuesto.dto.PresupuestoCreateDTO;
import com.api.presupuesto.api_presupuesto.dto.PresupuestoDTO;
import com.api.presupuesto.api_presupuesto.dto.PresupuestoUpdateDTO;
import com.api.presupuesto.api_presupuesto.model.Presupuesto;

@Component
public class PresupuestoMapper {

    public PresupuestoDTO toDto(Presupuesto presupuesto) {
        if (presupuesto == null) {
            return null;
        }
        
        PresupuestoDTO dto = new PresupuestoDTO();
        dto.setId(presupuesto.getId());
        dto.setNombre(presupuesto.getNombre());
        dto.setFecha(presupuesto.getFecha());
        dto.setMontoTotal(presupuesto.getMontoTotal());
        dto.setEstado(presupuesto.getEstado());
        
        return dto;
    }

    public Presupuesto toEntity(PresupuestoCreateDTO createDto) {
        if (createDto == null) {
            return null;
        }
        
        Presupuesto presupuesto = new Presupuesto();
        presupuesto.setNombre(createDto.getNombre());
        presupuesto.setFecha(createDto.getFecha());
        presupuesto.setMontoTotal(createDto.getMontoTotal());
        presupuesto.setEstado(createDto.getEstado());
        
        return presupuesto;
    }

    public void updateEntityFromDto(Presupuesto presupuesto, PresupuestoUpdateDTO updateDto) {
        if (presupuesto == null || updateDto == null) {
            return;
        }
        
        presupuesto.setNombre(updateDto.getNombre());
        presupuesto.setFecha(updateDto.getFecha());
        presupuesto.setMontoTotal(updateDto.getMontoTotal());
        presupuesto.setEstado(updateDto.getEstado());
    }

    public List<PresupuestoDTO> toDtoList(List<Presupuesto> presupuestos) {
        if (presupuestos == null) {
            return null;
        }
        
        return presupuestos.stream()
                .map(this::toDto)
                .toList();
    }
}
