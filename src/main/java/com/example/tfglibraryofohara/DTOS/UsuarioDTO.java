package com.example.tfglibraryofohara.DTOS;

import com.example.tfglibraryofohara.Entities.Usuario;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;


@Data
@NoArgsConstructor
public final class UsuarioDTO {
    private String nombre;
    private String apellidos;
    private String gmail;
    private String contrasenna;
    private String rol;

    public Usuario DTOtoModel() {
        Usuario usuario = new Usuario();
        usuario.setNombre(getNombre());
        usuario.setApellidos(getApellidos());
        usuario.setGmail(getGmail());
        usuario.setContrasenna(DigestUtils.sha256Hex(getContrasenna()));
        usuario.setRol(getRol());
        return usuario;
    }


    public UsuarioDTO(String nombre, String apellidos, String gmail, String contrasenna, String rol) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.gmail = gmail;
        this.contrasenna = DigestUtils.sha256Hex(contrasenna);
        this.rol = rol;
    }

    public UsuarioDTO modeltoDTO(Usuario usuario) {
        return new UsuarioDTO(usuario.getNombre(), usuario.getApellidos(), usuario.getGmail(), usuario.getContrasenna(), usuario.getRol());
    }

    public void codificarContra() {
        contrasenna = DigestUtils.sha256Hex(contrasenna);
    }
}
