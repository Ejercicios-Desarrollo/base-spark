package server;

import domain.controllers.UsuarioController;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;
import spark.utils.BooleanHelper;
import spark.utils.HandlebarsTemplateEngineBuilder;

public class Router {
    private static HandlebarsTemplateEngine engine;

    private static void initEngine() {
        Router.engine = HandlebarsTemplateEngineBuilder
                .create()
                .withDefaultHelpers()
                .withHelper("isTrue", BooleanHelper.isTrue)
                .build();
    }

    public static void init() {
        Router.initEngine();
        Spark.staticFileLocation("/public");
        Router.configure();
    }

    private static void configure(){
        UsuarioController usuarioController = new UsuarioController();
        // Spark.get("/hola", ((request, response) -> "Hola Tomi"));
        // Spark.get("/hola", ((request, response) -> "Hola " + request.queryParams("nombre")));
        // Spark.get("/hola/:nombre", ((request, response) -> "Hola " + request.params(("nombre"))));
        // usuarioController::mostrar funciona como callback
        // no lo llama en ese momento, solo cuando lo llaman
        Spark.get("/usuario/:id", usuarioController::mostrar, engine);
        Spark.get("/saludo", usuarioController::saludar, engine);
        Spark.get("/usuarios", usuarioController::mostrarTodos, engine);
    }
}
