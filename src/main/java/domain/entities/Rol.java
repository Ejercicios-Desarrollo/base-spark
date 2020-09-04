package domain.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rol")
public class Rol extends  EntidadPersistente{
    @Enumerated(EnumType.STRING)
    private TipoRol tipoRol;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Permiso> permisos;

    public Rol(){
        this.permisos = new ArrayList<>();
    }

    public Rol(TipoRol tipoRol) {
        this.tipoRol = tipoRol;
        this.permisos = new ArrayList<>();
    }

    public TipoRol getTipoRol() {
        return tipoRol;
    }

    public List<Permiso> getPermisos() {
        return permisos;
    }
}
