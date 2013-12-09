package controllers;

import java.util.List;

import auth.Auth;

import models.User;
import models.Address;
import models.ServiceLog;

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
						Auth.getUser().greekName,
						Auth.getUser().age,
						Auth.getUser().sex,
						Auth.getUser().graduationDate,
						Auth.getUser().phoneNumber,
						Auth.getUser().linkedIn,
						Auth.getUser().relationshipStatus,
						Auth.getUser().firstName,
						Auth.getUser().lastName,
						Auth.getUser().major,
						Auth.getUser().email,
						Auth.getUser().university,
						Auth.getUser().greekOrganization,
						Auth.getUser().facebookId,
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
			profileUser.major = data.major;
			profileUser.age = data.age; 
			profileUser.sex = data.sex;
			profileUser.graduationDate = data.graduationDate; 
			profileUser.phoneNumber = data.phoneNumber; 
			profileUser.linkedIn = data.linkedIn;
			profileUser.relationshipStatus = data.relationshipStatus;
			profileUser.greekName = data.greekName;
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

	public static Result getProfile(String username){
		if(Auth.isLoggedIn()){
			User viewUser = User.find
								.where()
								.eq("username", username)
								.findUnique();

			return ok(getProfile.render(viewUser));
		} else {
			return redirect("/");
		}
	}
	
	public static Result serviceLog(){
		if(Auth.isLoggedIn()){
			List<ServiceLog> serviceLogData = ServiceLog.find.fetch("user").findList();

			List<User> allAssociatedUsers = User.find
			        						.where()
			        						.eq("greekOrganization", Auth.getUser().greekOrganization)
			        						.eq("university", Auth.getUser().university)
			        						.findList();

			return ok(servicelog.render(
						serviceLogData,
						Auth.getUser().accessLevel,
						Auth.getUser().greekOrganization,
						Auth.getUser().university,
						allAssociatedUsers
					)
				);

		} else {
        	return redirect("/");
    	}	
	}

	public static Result addServiceLog(){
		if(Auth.isLoggedIn() && Auth.getUser().accessLevel == 1){
			ServiceModel data = new Form<ServiceModel>(ServiceModel.class).bindFromRequest().get();

			ServiceLog addServiceLog = new ServiceLog();

			addServiceLog.user = User.find.byId(Long.valueOf(data.userId));
			addServiceLog.university = Auth.getUser().university;
			addServiceLog.greekOrganization = Auth.getUser().greekOrganization;
			addServiceLog.serviceType = data.serviceType;
			addServiceLog.date = data.date;
			addServiceLog.hours = data.hours;
			addServiceLog.minutes = data.minutes;
			addServiceLog.comments = data.comments;

			addServiceLog.save();

			return redirect("/user/servicelog");
		} else {
			return redirect("/user/users");
		}
	}
	
	public static Result deleteServiceLog(Long id){
	if(Auth.isLoggedIn() && Auth.getUser().accessLevel == 1){
			
			ServiceLog.find.ref(id).delete();

			return redirect("/user/servicelog");
		} else {
			return redirect("/user/users");
		}
}
}