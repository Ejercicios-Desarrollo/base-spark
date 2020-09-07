package domain.entities;

import com.google.common.hash.Hashing;

import javax.persistence.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

@Entity
@Table(name = "usuario")
public class Usuario extends EntidadPersistente{
    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "nombre_usuario")
    private String nombreUsuario;

    @Column(name = "contrasenia")
    private String contrasenia;

    @Column(name = "fecha_nacimiento", columnDefinition = "DATE")
    private LocalDate fechaDeNacimiento;

    @Column(name = "telefono", nullable = true)
    private Integer telefono;

    @Column(name = "mail")
    private String mail;

    @ManyToOne
    @JoinColumn(name = "rol_id", referencedColumnName = "id")
    private Rol rol;

    public Usuario(){

    }

    public Usuario(String nombre, String apellido, String nombreUsuario) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.nombreUsuario = nombreUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public LocalDate getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public String getMail() {
        return mail;
    }

    public Rol getRol() {
        return rol;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public void setFechaDeNacimiento(LocalDate fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = Hashing.sha256().hashString(contrasenia, StandardCharsets.UTF_8).toString();
    }
}
