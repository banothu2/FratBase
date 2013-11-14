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

public class Test extends Controller {

    public static Result users() {
        List<User> users = User.find.all();

        return ok("Added "+ users.size() +"th username: " + users.get(users.size()-1).username);
    }


    public static Result create(){
        return ok(register.render("Hello"));
    }

    public static Result createUser(RegisterModel data) {
            
            User user = new User();
            user.name = data.firstName;
            user.username = data.username;
            user.passwordHash = User.hash(data.password);
            user.email = data.email;
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
        return ok(login.render("Hello"));
    }

    public static Result doLogin() {
            LoginModel data = new Form<LoginModel>(LoginModel.class).bindFromRequest().get();
            
             if (Auth.login(data.username, data.password)) {
                     return redirect("/");
             } else {
                     return ok(login.render(Auth.getUser().email));
             }
    }
    
    public static Result userList() {
            if (!Auth.isLoggedIn()) {
                    return redirect("/auth/login");
            }
            
            List<User> users = User.find.all();

            return ok(views.html.users.render(users));
    }
}