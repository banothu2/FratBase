package controllers;

import play.*;
import play.mvc.*;

import views.html.*;
import auth.Auth;

public class Application extends Controller {

    public static Result index() {
    	if(!Auth.isLoggedIn()){
        	return ok(index.render("Your."));
    	} else {
        	return ok(index.render("Your new application is ."));
    	}
    }

    public static Result loggedIn(){
    	return ok(loggedIn.render("You are logged in!"));
    }

}
