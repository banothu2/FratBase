package controllers;

import java.util.List;

import models.User;
import play.*;
import play.mvc.*;
import viewmodels.*;
import views.html.loggedIn;
import views.html.helper.form;
import play.data.*;

public class Test extends Controller {

    public static Result users() {
        List<User> users = User.find.all();
        
        return ok(users.get(0).name);
    }
    
    public static Result create() {
    	
    	User user = new User();
    	user.name = "Blah";
    	user.username = "username";
    	user.passwordHash = User.hash("password");
    	user.email = "email";
    	user.save();

    	return ok("wuhi: " + user.id);
    }
    
    public static Result login(){
    	return ok(views.html.login.render());
    }
    
    public static Result doLogin() {
    	LoginModel data = new Form<LoginModel>(LoginModel.class).bindFromRequest().get();
    	return ok(data.email + data.password);
    }
}


