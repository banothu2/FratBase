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


public class Users extends Controller {
	
	public static Result profile(){
		if(Auth.isLoggedIn()){
        	List<User> users = User.find
        						.where()
        						.eq("greek.id", Auth.getUser().greek.id)
        						.findList();
        	Address address = Address.find
        						.where()
        						.eq("user.id", Auth.getUser().id)
        						.findUnique();


        	List<ServiceLog> serviceLog = ServiceLog.find
        								 	  .where()
        								 	  .eq("user.id", Auth.getUser().id)
        								 	  .findList();

        	int philanthropyHours = 0;
        	int fundraisingHours = 0;


        	for(int i = 0; i < serviceLog.size(); i++){
        		if((serviceLog.get(i).serviceType).equals("Philanthropy")){
        			philanthropyHours += serviceLog.get(i).hours + (serviceLog.get(i).minutes / 60);
        		} else {
        			fundraisingHours += serviceLog.get(i).hours + (serviceLog.get(i).minutes / 60);
        		}
        	}
        	int totalHours = philanthropyHours + fundraisingHours;
            double philoPercentage = (philanthropyHours / (double) totalHours) *100;
            double fundraisingPercentage = (fundraisingHours / (double) totalHours)*100;

        	User userProfile = Auth.getUser();

			return ok(profile.render(
						userProfile,
						users,
						address,
						philanthropyHours,
						fundraisingHours,
						totalHours,
						philoPercentage, 
						fundraisingPercentage
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

			addAddress.user = Auth.getUser();
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
        						.eq("user.id", Auth.getUser().id)
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
			Address userAddress = Address.find
										   .where()
										   .eq("user.id", viewUser.id)
										   .findUnique();

			return ok(getProfile.render(
									Auth.getUser(),
									viewUser,
									userAddress	
			));
		} else {
			return redirect("/");
		}
	}
	
	public static Result serviceLog(){
		if(Auth.isLoggedIn()){
			List<ServiceLog> serviceLogData = ServiceLog.find
														.fetch("user")
														.where()
														.eq("greek.id", Auth.getUser().greek.id)
														.findList();

			List<User> allAssociatedUsers = User.find
			        						.where()
			        						.eq("greek.id", Auth.getUser().greek.id)
			        						.findList();

			User userData = Auth.getUser();

			return ok(servicelog.render(
						serviceLogData,
						userData,
						userData.greek.name,
						userData.greek.university,
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
			addServiceLog.greek = Auth.getUser().greek;
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