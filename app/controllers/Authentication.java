package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Authentication extends Controller {
	
    public static Result registerPageRender() {
        return ok(register.render("Please Register at Frat-Base"));
    }

    public static Result registerUser(){
    	return ok(register.render("hello"));
    }

	public class User {
		@Required
	    public String email;
	    public String password;

	    public String validate() {
	        if(authenticate(email,password) == null) {
	            return "Invalid email or password";
	        }
	        return null;
	    }
	}

	Form<User> userForm = form(User.class);






}
