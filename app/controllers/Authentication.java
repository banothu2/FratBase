package controllers;

import java.util.List;

import auth.Auth;

import models.*;
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

        return ok(register.render(
                            Greek.find.all()
            ));
    }

    public static void createUser(RegisterModel data) {
    	
    	User user = new User();
    	user.firstName = data.firstName;
        user.lastName = data.lastName;
    	user.username = data.username;
    	user.passwordHash = User.hash(data.password);
    	user.email = data.email;
        // user.age = data.age;
        // user.sex = data.sex;
        // user.graduationDate = data.graduationDate;
        user.greek = Greek.find.byId(Long.valueOf(data.greekId));
        // user.greekName = data.greekName;
        // user.profilePicture = data.profilePicture;
        // user.facebookId = data.facebookId;
        // user.phoneNumber = data.phoneNumber; 
        // user.linkedIn = data.linkedIn;
        // user.resume = data.resume;
        // user.status = data.status;
        user.accessLevel = data.accessLevel;
        // user.relationshipStatus = data.relationshipStatus;
        // user.major = data.major; 

    	user.save();

    	//return ok("wuhi: " + user.id);
    }

    public static Result doRegister() {
    	RegisterModel data = new Form<RegisterModel>(RegisterModel.class).bindFromRequest().get();
        createUser(data);

        return redirect("/auth/login");
        //return ok("Created user with email: " + data.email);
        //return ok(data.email +" "+ data.password + " "+ data.firstName + " "+ data.lastName + " "+ data.university + " "+ data.greek);
    }


    
    public static Result login(){
        if(Auth.isLoggedIn()){
            return redirect("/user/profile");

        } else {
            return ok(login.render("Hello"));
        }
    }

    public static Result doLogin() {
    	LoginModel data = new Form<LoginModel>(LoginModel.class).bindFromRequest().get();
    	
     	if (Auth.login(data.username, data.password)) {
     		return redirect("/user/profile");
     	} else {
     		return ok(login.render("The entered username/password does not match."));
     	}
    }

    public static Result userList() {

            if (!Auth.isLoggedIn()) {
                    return redirect("/auth/login");
            }

            List<User> users = User.find.all();

            return ok(views.html.users.render(
                                        users,
                                        Auth.getUser()
                ));
    }
}



