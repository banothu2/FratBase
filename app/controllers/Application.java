package controllers;

import play.*;
import play.mvc.*;

import views.html.*;
import auth.Auth;

public class Application extends Controller {

    public static Result index() {
    
        return redirect("/auth/login");
    /*	if(!Auth.isLoggedIn()){
        	return ok(index.render("Logg"));
    	} else {
        	return ok(index.render("Your new application is ."));
    	}
    */
    }


}
