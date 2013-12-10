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

		//List<GreekRank> listOfAllGreeks = Greek.find.findList();

		RawSql greekListOfAll = RawSqlBuilder.parse("SELECT id, name, university FROM greek")
		.columnMapping("id", "greek_id").create();

		Query<GreekRank> greekRank = Ebean.find(GreekRank.class);
		List<GreekRank> listOfAllGreeks = greekRank.setRawSql(greekListOfAll).findList();


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

		// service hours per user
		RawSql greekRankByNumberOfServiceHours = RawSqlBuilder.parse("SELECT greek_id, SUM(hours) FROM service_log GROUP BY greek_id")
		.columnMapping("greek_id", "greek_id")
		.columnMapping("SUM(hours)", "numberOfServiceHours").create();

		Query<GreekService> greekService = Ebean.find(GreekService.class);
		List<GreekService> service = greekService.setRawSql(greekRankByNumberOfServiceHours).findList();


		for(int i = 0; i < listOfAllGreeks.size(); i++){
			for(int j = 0; j < facebookFriends.size(); j++){
				if(facebookFriends.get(j).greek_id == listOfAllGreeks.get(i).greek_id){
					listOfAllGreeks.get(i).commonFriendCount = facebookFriends.get(j).commonFriendCount;
				}
			}

			for(int k = 0; k < events.size(); k++){
				if(events.get(k).greek_id == listOfAllGreeks.get(i).greek_id){
					listOfAllGreeks.get(i).numberOfEvents = events.get(k).numberOfEvents;
				}
			}

			for(int l = 0; l < service.size(); l++){
				if(service.get(l).greek_id == listOfAllGreeks.get(i).greek_id){
					listOfAllGreeks.get(i).numberOfServiceHours = service.get(l).numberOfServiceHours;
				}
			}

			// # of friends in total = 1208 
			// number of service hours divided by number of users in each greek

			listOfAllGreeks.get(i).rank = listOfAllGreeks.get(i).commonFriendCount + listOfAllGreeks.get(i).numberOfServiceHours + listOfAllGreeks.get(i).numberOfEvents;
		}

		Collections.sort(listOfAllGreeks);
		for(int i = 0; i < listOfAllGreeks.size(); i++){
			listOfAllGreeks.get(i).index = i+1;
		}

		return ok(greekRankResult.render(listOfAllGreeks));

		//return redirect("/rank/result");
		// return ok(greekRankResult.render(listOfAllGreeks));

	}


	//public static Result getListOfGreeks(){
		//List<Greek> greeks = Greek.find
		//						  .where()
		//						  .eq("university", Auth.getUser.greek.university)
		//						  .findList();


	//}

}
