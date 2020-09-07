package domain.controllers;

import domain.entities.Rol;
import domain.entities.Usuario;
import domain.repositories.Repositorio;
import domain.repositories.daos.DAOHibernate;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsuarioController {
    private Repositorio<Usuario> repoUsuarios;

    public UsuarioController(){
        this.repoUsuarios = new Repositorio<>(new DAOHibernate<>(Usuario.class));
    }

    // semana que viene, esto es stateful
    public Response login(Request request, Response response){
        //if usuario es valido
        request.session(true);
        request.session().attribute("id", 1);
        return response;
    }

    public ModelAndView mostrar(Request request, Response response){
        // request.session().attribute("id");
        // dentro de repoUsuarios.buscar()
        Integer idUsuarioBuscado = new Integer(request.params("id"));
        Usuario usuarioBuscado = this.repoUsuarios.buscar(idUsuarioBuscado);

        Repositorio<Rol> repoRoles = new Repositorio<>(new DAOHibernate<>(Rol.class));
        List<Rol> roles = repoRoles.buscarTodos();

        Map<String, Object> parametros = new HashMap<>();

        parametros.put("usuario", usuarioBuscado);
        parametros.put("roles", roles);

        return new ModelAndView(parametros, "usuario.hbs");
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

    public Response modificar(Request request, Response response){
        Integer idUsuario = new Integer(request.params("id"));
        Usuario usuarioAModificar = this.repoUsuarios.buscar(idUsuario);
        asignarAtributosA(usuarioAModificar, request);
        this.repoUsuarios.modificar(usuarioAModificar);
        response.redirect("/usuarios");
        return response;
    }

    public Response guardar(Request request, Response response){
        Usuario usuario = new Usuario();
        asignarAtributosA(usuario, request);
        this.repoUsuarios.agregar(usuario);
        response.redirect("/usuarios");
        return response;
    }

    public void asignarAtributosA(Usuario usuario, Request request){
        usuario.setNombreUsuario(request.queryParams("nombreDeUsuario"));
        usuario.setNombre(request.queryParams("nombre"));
        usuario.setApellido(request.queryParams("apellido"));
        usuario.setMail(request.queryParams("email"));

        if(request.queryParams("telefono") != null && !request.queryParams("telefono").isEmpty()){
            usuario.setTelefono(new Integer(request.queryParams("telefono")));
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //convert String to LocalDate
        String date = request.queryParams("fechaDeNacimiento");
        System.out.println("date: " + date);
        LocalDate localDate = LocalDate.parse(date, formatter);

        usuario.setFechaDeNacimiento(localDate);

        usuario.setContrasenia(request.queryParams("password"));
    }

    public ModelAndView crear(Request request, Response response){
        Repositorio<Rol> repoRoles = new Repositorio<>(new DAOHibernate<>(Rol.class));
        List<Rol> roles = repoRoles.buscarTodos();
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("roles", roles);
        return new ModelAndView(parametros, "usuario.hbs");
    }

    public Response eliminar(Request request, Response response){
        Integer idUsuarioBuscado = new Integer(request.params("id"));
        Usuario usuarioBuscado = this.repoUsuarios.buscar(idUsuarioBuscado);
        this.repoUsuarios.eliminar(usuarioBuscado);

        return response;
    }
}
