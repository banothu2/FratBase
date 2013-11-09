package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Your new application is Ready NOt!."));
    }

    public static Result loggedIn(){
    	return ok(loggedIn.render("You are logged in!"));
    }
}
