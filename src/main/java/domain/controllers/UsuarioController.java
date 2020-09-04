package domain.controllers;

import domain.entities.Usuario;
import domain.repositories.Repositorio;
import domain.repositories.daos.DAOHibernate;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsuarioController {
    private Repositorio<Usuario> repoUsuarios;

    public UsuarioController(){
        this.repoUsuarios = new Repositorio<>(new DAOHibernate<>(Usuario.class));
    }

    public ModelAndView mostrar(Request request, Response response){
        Integer idUsuarioBuscado = new Integer(request.params("id"));

        Usuario usuarioBuscado = this.repoUsuarios.buscar(idUsuarioBuscado);

        Map<String, Object> parametros = new HashMap<>();

        parametros.put("usuario", usuarioBuscado);

        return new ModelAndView(parametros, "saludo.hbs");
    }

    public ModelAndView saludar(Request request, Response response){
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("persona", request.queryParams("nombre"));
        return new ModelAndView(parametros, "saludo.hbs");
    }

    public ModelAndView mostrarTodos(Request request, Response response){
        List<Usuario> usuarios = this.repoUsuarios.buscarTodos();
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("usuarios", usuarios);

        return new ModelAndView(parametros, "usuarios.hbs");
    }
}
