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

        	List<User> users = User.find.all();

			return ok(profile.render(
						Auth.getUser().firstName,
						Auth.getUser().lastName,
						Auth.getUser().email,
						Auth.getUser().university,
						Auth.getUser().greekOrganization,
						Auth.getUser().facebookId,
						Auth.getUser().sex,
						Auth.getUser().accessLevel, 
						users
					)
				);

		} else {
        	return redirect("/");
    	}	
	}
	public static Result profileUpdate(){
		if(Auth.isLoggedIn()){
			ProfileUpdate data = new Form<ProfileUpdate>(ProfileUpdate.class).bindFromRequest().get();
			User profileUser = Auth.getUser();
			profileUser.firstName = data.firstName;
			profileUser.lastName = data.lastName;
			profileUser.email = data.email;
			profileUser.save();

			return redirect("/user/profile");

		} else {
			return redirect("/");
		}
	}

}