package domain.dtos;

import domain.entities.Usuario;

// Se puede usar como static class en la clase pricipal
public class UsuarioDto {
    private String nombre;
    private String apellido;

    public UsuarioDto(Usuario usuario){
        this.nombre = usuario.getNombre();
        this.apellido = usuario.getApellido();
    }
}
