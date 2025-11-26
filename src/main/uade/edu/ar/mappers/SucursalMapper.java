package main.uade.edu.ar.mappers;

import main.uade.edu.ar.dto.SucursalDto;
import main.uade.edu.ar.model.Sucursal;

public class SucursalMapper {

    private SucursalMapper() {
    }

    public static Sucursal toModel(SucursalDto sucursalDto) {
        return new Sucursal(
                sucursalDto.getId(),
                sucursalDto.getNumero(),
                sucursalDto.getDireccion(),
                UsuarioMapper.toModel(sucursalDto.getResponsableTecnico())
        );
    }

    public static SucursalDto toDto(Sucursal sucursal) {
        return new SucursalDto(
                sucursal.getId(),
                sucursal.getNumero(),
                sucursal.getDireccion(),
                UsuarioMapper.toDto(sucursal.getResponsableTecnico())
        );
    }
}

