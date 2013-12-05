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

public class Api extends Controller {

    public static Result addFacebookData() {
        FacebookDataModel data = new Form<FacebookDataModel>(FacebookDataModel.class).bindFromRequest().get();
        User facebookUser = Auth.getUser();
        facebookUser.sex = data.gender;
        facebookUser.facebookId = data.id;
        facebookUser.save();
        return ok("Added");
    }



}



