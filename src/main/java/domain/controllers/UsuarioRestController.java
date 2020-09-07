package domain.controllers;

import com.google.gson.Gson;
import domain.dtos.UsuarioDto;
import domain.entities.Usuario;
import domain.repositories.Repositorio;
import domain.repositories.daos.DAOHibernate;
import spark.Request;
import spark.Response;

public class UsuarioRestController {
    private Repositorio<Usuario> repoUsuarios;

    public UsuarioRestController() {
        this.repoUsuarios = repoUsuarios = new Repositorio<>(new DAOHibernate<>(Usuario.class));
    }

    public String mostrar(Request request, Response response){
        Usuario usuario = this.repoUsuarios.buscar(new Integer(request.params("id")));
        Gson gson = new Gson();
        String jsonUsuario = gson.toJson(new UsuarioDto(usuario));
        return jsonUsuario;
    }
}
