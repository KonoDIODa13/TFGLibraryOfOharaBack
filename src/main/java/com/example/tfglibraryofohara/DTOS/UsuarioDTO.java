package com.example.tfglibraryofohara.DTOS;

import com.example.tfglibraryofohara.Entities.Usuario;

public record UsuarioDTO(String nombre, String apellidos, String contrasenna, String rol) {
    public Usuario DTOtoModel() {
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellidos(apellidos);
        usuario.setContrasenna(contrasenna);
        usuario.setRol(rol);
        return usuario;
    }

    public UsuarioDTO modeltoDTO(Usuario usuario) {
        return new UsuarioDTO(usuario.getNombre(), usuario.getApellidos(), usuario.getContrasenna(), usuario.getRol());
    }
}
