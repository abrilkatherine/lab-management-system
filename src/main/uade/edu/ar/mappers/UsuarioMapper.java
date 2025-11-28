package main.uade.edu.ar.mappers;

import main.uade.edu.ar.dto.UsuarioDto;
import main.uade.edu.ar.model.Usuario;

public class UsuarioMapper {

    private UsuarioMapper() {
    }

    public static Usuario toModel(UsuarioDto usuarioDto) {
        return new Usuario(
                usuarioDto.getId(),
                usuarioDto.getNombre(),
                usuarioDto.getContrasenia(),
                usuarioDto.getNacimiento(),
                usuarioDto.getRol()
        );
    }

    public static UsuarioDto toDto(Usuario usuario) {
        return new UsuarioDto(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getContrasenia(),
                usuario.getNacimiento(),
                usuario.getRol()
        );
    }
}


