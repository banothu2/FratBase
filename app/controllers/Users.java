package controllers;

import java.util.List;

import auth.Auth;

import models.User;
import models.Address;

import play.*;
import play.mvc.*;
import viewmodels.*;
import views.html.*;
import views.html.helper.form;
import play.data.*;


public class Users extends Controller {
	
	public static Result profile(){
		if(Auth.isLoggedIn()){

        	//List<User> users = User.find.all();
        	List<User> users = User.find
        						.where()
        						.eq("greekOrganization", Auth.getUser().greekOrganization)
        						.eq("university", Auth.getUser().university)
        						.findList();
        	Address address = Address.find
        						.where()
        						.eq("userId", Auth.getUser().id)
        						.findUnique();

        	//Address address = new Address(); 
        	//address.addressLineOne = "hello";
        	//find row where userId = Auth.getUser().id;

			return ok(profile.render(
						Auth.getUser().id,
						Auth.getUser().firstName,
						Auth.getUser().lastName,
						Auth.getUser().email,
						Auth.getUser().university,
						Auth.getUser().greekOrganization,
						Auth.getUser().facebookId,
						Auth.getUser().sex,
						Auth.getUser().accessLevel, 
						users,
						address
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

	public static Result addAddress(){
		if(Auth.isLoggedIn()){
			AddressUpdate data = new Form<AddressUpdate>(AddressUpdate.class).bindFromRequest().get();

			User user = Auth.getUser();
			Address addAddress = new Address();


			addAddress.userId = Auth.getUser().id;
			addAddress.firstName = Auth.getUser().firstName;
			addAddress.lastName = Auth.getUser().lastName; 
			addAddress.facebookId = Auth.getUser().facebookId;
			addAddress.addressLineOne = data.addressLineOne;
			addAddress.addressLineTwo = data.addressLineTwo;
			addAddress.city = data.city;
			addAddress.state = data.state;
			addAddress.zip = data.zip;
			addAddress.country = data.country;
			addAddress.addressType = data.addressType;
			addAddress.save();

			return redirect("/user/profile");

		} else {
			return redirect("/");
		}
	}

	public static Result updateAddress(){
		if(Auth.isLoggedIn()){
			AddressUpdate data = new Form<AddressUpdate>(AddressUpdate.class).bindFromRequest().get();

			User user = Auth.getUser();
			Address updateAddress = Address.find
        						.where()
        						.eq("userId", Auth.getUser().id)
        						.findUnique();

			updateAddress.addressLineOne = data.addressLineOne;
			updateAddress.addressLineTwo = data.addressLineTwo;
			updateAddress.city = data.city;
			updateAddress.state = data.state;
			updateAddress.zip = data.zip;
			updateAddress.country = data.country;
			updateAddress.addressType = data.addressType;
			updateAddress.save();

			return redirect("/user/profile");

		} else {
			return redirect("/");
		}
	}
}