package domain.middlewares;

import spark.Request;
import spark.Response;
import spark.Spark;

public class AuthMiddleware {

    public static Response redirigirSiHaySesion(Request request, Response response){
        if(!request.session().isNew()){
            response.redirect("/usuarios");
        }
        return response;
    }

    public static Response redirigirSiNoHaySesion(Request request, Response response){
        Spark.halt(401, "No estás autorizado");
        return response;
    }
}
