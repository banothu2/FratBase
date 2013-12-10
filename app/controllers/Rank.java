package controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import auth.Auth;

import models.*;

import play.*;
import play.mvc.*;
import viewmodels.*;
import views.html.*;
import views.html.helper.form;
import play.data.*;


public class Rank extends Controller {
	public static Result renderRankPage(){

		return ok(rank.render("welcome!"));
	}

	public static Result userFacebookFriends(){
		FacebookFriendsModel data = new Form<FacebookFriendsModel>(FacebookFriendsModel.class).bindFromRequest().get();

		return ok("Hello");
	}

}