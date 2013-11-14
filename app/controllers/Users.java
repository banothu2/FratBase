package controllers;

import java.util.List;

import auth.Auth;

import models.User;
import play.*;
import play.mvc.*;
import viewmodels.*;
import views.html.*;
import views.html.helper.form;
import play.data.*;

public class Users extends Controller {
	
	public static Result profile(){
		if(Auth.isLoggedIn()){
			return ok(profile.render(
						Auth.getUser().name,
						Auth.getUser().email,
						Auth.getUser().university,
						Auth.getUser().greek
					)
				);

		} else {
        	return redirect("/");
    	}	
	}

}