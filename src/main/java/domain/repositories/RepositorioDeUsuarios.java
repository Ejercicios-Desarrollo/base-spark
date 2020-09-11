package domain.repositories;

import domain.entities.Usuario;
import domain.repositories.daos.BusquedaCondicional;
import domain.repositories.daos.DAO;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class RepositorioDeUsuarios extends Repositorio<Usuario>{
    public RepositorioDeUsuarios(DAO<Usuario> dao) {
        super(dao);
    }

    public Boolean existe(String nombreUsuario, String contrasenia){
        return buscarUsuario(nombreUsuario, contrasenia) != null;
    }

    public Usuario buscarUsuario(String nombreUsuario, String contrasenia){
        return this.dao.buscar(condicionUsuarioYContrasenia(nombreUsuario, contrasenia));
    }

    private BusquedaCondicional condicionUsuarioYContrasenia(String nombreDeUsuario, String contrasenia){
        CriteriaBuilder criteriaBuilder = criteriaBuilder();
        CriteriaQuery<Usuario> usuarioQuery = criteriaBuilder.createQuery(Usuario.class);

        Root<Usuario> condicionRaiz = usuarioQuery.from(Usuario.class);
        // SELECT u.* FROM usuario

        Predicate condicionNombreDeUsuario = criteriaBuilder.equal(condicionRaiz.get("nombreUsuario"), nombreDeUsuario);
        // u.nombreDeUsuario = ''

        Predicate condicionContrasenia = criteriaBuilder.equal(condicionRaiz.get("contrasenia"), contrasenia);
        // u.contrasenia = ''

        Predicate condicionExisteUsuario = criteriaBuilder.and(condicionNombreDeUsuario, condicionContrasenia);
        // u.nombreDeUsuario = '' AND u.contrasenia = ''

        usuarioQuery.where(condicionExisteUsuario);
        // SELECT u.* FROM usuario u WHERE u.nombreDeUsuario = '' AND u.contrasenia = ''

        return new BusquedaCondicional(usuarioQuery, null);
    }
}
