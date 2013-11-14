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

public class Authentication extends Controller {

    public static Result users() {
        List<User> users = User.find.all();

        return ok("Added "+ users.size() +"th username: " + users.get(users.size()-1).username);
    }


    public static Result create(){
        return ok(register.render("apple", "test"));
    }

    public static Result createUser(RegisterModel data) {
    	
    	User user = new User();
    	user.name = data.firstName + " " + data.lastName;
    	user.username = data.username;
    	user.passwordHash = User.hash(data.password);
    	user.email = data.email;
        user.university = data.university;
        user.greek = data.greek;
    	user.save();

    	return ok("wuhi: " + user.id);
    }

    public static Result doRegister() {
    	RegisterModel data = new Form<RegisterModel>(RegisterModel.class).bindFromRequest().get();
        createUser(data);
        return ok("Created user with email: " + data.email);
        //return ok(data.email +" "+ data.password + " "+ data.firstName + " "+ data.lastName + " "+ data.university + " "+ data.greek);
    }


    
    public static Result login(){
        if(Auth.isLoggedIn()){
            return redirect("/");

        } else {
            return ok(login.render("Hello"));
        }
    }

    public static Result doLogin() {
    	LoginModel data = new Form<LoginModel>(LoginModel.class).bindFromRequest().get();
    	
     	if (Auth.login(data.username, data.password)) {
     		return redirect("/");
     	} else {
     		return ok(login.render("The entered username/password does not match."));
     	}
    }
}



