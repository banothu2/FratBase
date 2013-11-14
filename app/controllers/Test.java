package controllers;

import java.util.List;

import models.User;
import play.*;
import play.mvc.*;
import viewmodels.*;
import views.html.*;
import views.html.loggedIn;
import views.html.helper.form;
import play.data.*;

public class Test extends Controller {

    public static Result users() {
        List<User> users = User.find.all();

        return ok("Added "+ users.size() +"th username: " + users.get(users.size()-1).username);
    }


    public static Result create(){
        return ok(register.render("Hello"));
    }

    public static Result createUser(LoginModel data) {
    	
    	User user = new User();
    	user.name = data.firstName;
    	user.username = data.username;
    	user.passwordHash = User.hash(data.password);
    	user.email = data.email;
    	user.save();

    	return ok("wuhi: " + user.id);
    }

    public static Result doRegister() {
    	LoginModel data = new Form<LoginModel>(LoginModel.class).bindFromRequest().get();
        createUser(data);
        return ok("Created user with email: " + data.email);
        //return ok(data.email +" "+ data.password + " "+ data.firstName + " "+ data.lastName + " "+ data.university + " "+ data.greek);
    }


    
    public static Result login(){
        return ok(login.render("Hello"));
    }

    public static Result doLogin(){
        
    }
    
}



