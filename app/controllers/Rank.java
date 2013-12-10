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
import com.avaje.ebean.*;


public class Rank extends Controller {
	public static Result renderRankPage(){

		return ok(rank.render("welcome!"));
	}

	public static Result userFacebookFriends(){
		FacebookFriendsModel friendsList = new Form<FacebookFriendsModel>(FacebookFriendsModel.class).bindFromRequest().get();
		
		String[] friends = friendsList.friends.split(",");



		//SELECT greek_id COUNT(*) FROM user u WHERE facebook_id IN () GROUP BY greek_id;
		RawSql greekRankByFriendsInCommon = RawSqlBuilder.parse("SELECT greek_id, COUNT(*) FROM user WHERE facebook_id IN ("+friendsList.friends+") GROUP BY greek_id")
		.columnMapping("greek_id", "greek_id")
		.columnMapping("COUNT(*)", "commonFriendCount").create();

		Query<GreekFacebook> greekFacebook = Ebean.find(GreekFacebook.class);
		List<GreekFacebook> facebookFriends = greekFacebook.setRawSql(greekRankByFriendsInCommon).findList();
		// gives university rank based on how many friends you have in it 

		RawSql greekRankByEventCount = RawSqlBuilder.parse("SELECT greek_id, COUNT(*) FROM event GROUP BY greek_id")
		.columnMapping("greek_id", "greek_id")
		.columnMapping("COUNT(*)", "numberOfEvents").create();

		Query<GreekEvent> greekEvent = Ebean.find(GreekEvent.class);
		List<GreekEvent> events = greekEvent.setRawSql(greekRankByEventCount).findList();



		
		return ok("" + events.get(0).numberOfEvents);
	}




	//public static Result getListOfGreeks(){
		//List<Greek> greeks = Greek.find
		//						  .where()
		//						  .eq("university", Auth.getUser.greek.university)
		//						  .findList();


	//}

}
