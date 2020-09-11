package server;

import domain.controllers.LoginController;
import domain.controllers.UsuarioController;
import domain.controllers.UsuarioRestController;
import domain.middlewares.AuthMiddleware;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;
import spark.utils.BooleanHelper;
import spark.utils.EqualsHelper;
import spark.utils.HandlebarsTemplateEngineBuilder;

public class Router {
    private static HandlebarsTemplateEngine engine;

    private static void initEngine() {
        Router.engine = HandlebarsTemplateEngineBuilder
                .create()
                .withDefaultHelpers()
                .withHelper("isTrue", BooleanHelper.isTrue)
                .withHelper("equals", new EqualsHelper())
                .build();
    }

    public static void init() {
        Router.initEngine();
        Spark.staticFileLocation("/public");
        Router.configure();
    }

    private static void configure() {
        UsuarioController usuarioController = new UsuarioController();
        UsuarioRestController usuarioRestController = new UsuarioRestController();
        LoginController loginController = new LoginController();

        Spark.before("/", AuthMiddleware::redirigirSiHaySesion);

        Spark.get("/", loginController::inicio, engine);

        // Spark.before("/*", AuthMiddleware::redirigirSiNoHaySesion); cuidado con /login y /logout
        // llamar a middleware para validar sesion

        Spark.post("/login", loginController::login);

        Spark.get("/logout", loginController::logout);

        Spark.get("/usuario/:id", usuarioController::mostrar, engine);

        Spark.get("/usuarios", usuarioController::mostrarTodos, engine);

        Spark.post("/usuario/:id", usuarioController::modificar);

        Spark.post("/usuario", usuarioController::guardar);

        Spark.get("/usuario", usuarioController::crear, engine);

        Spark.delete("/usuario/:id", usuarioController::eliminar);

        Spark.get("/api/usuario/:id", usuarioRestController::mostrar);

        // Spark.after()
        Spark.before("/api/*", ((request, response) -> response.type("application/json")));
    }
}
