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


public class Events extends Controller {
	public static Result renderEventPage(){
		if(Auth.isLoggedIn()){
			List<Event> listOfEvents = Event.find
								.where()
								.eq("greek.id", Auth.getUser().greek.id)
								.findList();

			return ok(events.render(
								listOfEvents,
								Auth.getUser()
				));

		} else {
			return redirect("/");
		}
	}

	public static Result createEvent(){
		if(Auth.isLoggedIn()){

			EventModel data = new Form<EventModel>(EventModel.class).bindFromRequest().get();

			Event event = new Event();

			event.greek = Auth.getUser().greek;
			event.name = data.name;
			event.startDateAndTime = data.startDateAndTime;
			event.endDateAndTime = data.endDateAndTime;
			event.openEvent = data.openEvent;
			event.location = data.location;
			event.eventType = data.eventType;
			event.save();

			return redirect("/user/events");

		} else {
			return redirect("/");
		}
	}

}