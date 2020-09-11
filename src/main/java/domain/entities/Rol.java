package domain.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rol")
public class Rol extends  EntidadPersistente{
    @Column(name = "tipoRol")
    private String tipoRol;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Permiso> permisos;

    public Rol(){
        this.permisos = new ArrayList<>();
    }

    public String getTipoRol() {
        return tipoRol;
    }

    public void setTipoRol(String tipoRol) {
        this.tipoRol = tipoRol;
    }

    public List<Permiso> getPermisos() {
        return permisos;
    }
}
