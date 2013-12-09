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
	
	public static Result suggestEvent(){
		if(Auth.isLoggedIn()){

			SuggestEventModel data = new Form<SuggestEventModel>(SuggestEventModel.class).bindFromRequest().get();

			List<Event> events = Event.find
									  .where()
									  .ge("startDateAndTime", data.startDateAndTime)
									  .le("endDateAndTime", data.endDateAndTime)
									  .findList();
			
			List<RankedEvent> rankedEvents = rankEvents(events);
			List<RankedEvent> schedule = scheduleEvents(rankedEvents);
			
			events = new ArrayList<Event>();
			
			for (RankedEvent event : schedule) {
				events.add(event.event);
			}

			//return ok(data.startDateAndTime.toString());
			return ok(suggestEvents.render(
									events
				));
		} else {
			return redirect("/");
		}
	}
	
	private static List<RankedEvent> rankEvents(List<Event> events) {
		List<RankedEvent> ranked = new ArrayList<RankedEvent>();
		int[] numberOfAttending = new int[events.size()];
		int maxAttending = 1; // Avoid division by zero

		for (int i = 0; i < events.size(); i++) {
			numberOfAttending[i] = Attend.find.where().eq("event.id", events.get(i).id).findRowCount();
			maxAttending = Math.max(numberOfAttending[i], maxAttending);
		}
		
		for (int i = 0; i < events.size(); i++) {
			Event event = events.get(i);
			double score = 1;
			
			// If I have marked me as attending give a huge bonus
			boolean iAttend = Attend.find.where()
				.eq("user.id", Auth.getUserId())
				.eq("event.id", event.id)
				.findRowCount() > 0;
			
			if (iAttend) {
				score += 10;
			}
				
			// How many are going to attend this compared to other events?
			score += (double)numberOfAttending[i] / maxAttending;

			// Add some score if he has previously attended this kind of event
			int attendedOfThisKind = Attend.find.where()
				.eq("user.id", Auth.getUserId())
				.eq("event.eventType", event.eventType)
				.findRowCount();
			
			int attended = Attend.find.where()
					.eq("user.id", Auth.getUserId())
					.findRowCount();
			
			if (attended >= 1)
				score += ((double)attendedOfThisKind / attended);
			
			ranked.add(new RankedEvent(event, score));
		}
		
		return ranked;
	}
	
	private static List<RankedEvent> scheduleEvents(List<RankedEvent> events) {
		if (events.isEmpty())
			return events;
		
		events = new ArrayList<RankedEvent>(events);
		Collections.sort(events);
		double[] mem = new double[events.size()];
		int[] picked = new int[events.size()];
		int[] next = new int[events.size()];
		
		for (int i = 0; i < mem.length; i++) {
			mem[i] = -1;
			next[i] = events.size();
			picked[i] = -1;
		}

		opt(0, events, mem, picked, next);	
		
		List<RankedEvent> schedule = new ArrayList<RankedEvent>();
		int cur = 0;
		
		while (cur < events.size()) {
			schedule.add(events.get(picked[cur]));
			cur = next[cur];
		}
		
		return schedule;
	}
	
	private static double opt(int from, List<RankedEvent> events, double[] mem, int[] picked, int next[]) {
		if (from >= events.size()) {
			return 0;
		}
		
		if (mem[from] >= 0) {
			return mem[from];
		}
		
		double bestScore = -1;

		for (int i = from; i < events.size(); i++) {
			int j = i + 1;
			
			while (j < events.size() && events.get(j).event.startDateAndTime.compareTo(events.get(i).event.endDateAndTime) < 0) j++;

			double score = events.get(i).rank + opt(j, events, mem, picked, next);
			
			if (score > bestScore) {
				picked[from] = i;
				next[from] = j;
				mem[from] = bestScore;
				bestScore = score;
			}
		}
		
		return bestScore;
	}

	public static Result attendEvent(int id){
		if(Auth.isLoggedIn()){

			Attend attend = new Attend();

			attend.user = Auth.getUser();
			attend.event = Event.find.byId(Long.valueOf(id));
			attend.save();
			
			return redirect("/user/events");
		} else {
			return redirect("/");
		}
	}
}

class RankedEvent implements Comparable<RankedEvent> {
	public Event event;
	public double rank;
	
	public RankedEvent(Event event, double rank) {
		this.event = event;
		this.rank = rank;
	}

	public int compareTo(RankedEvent arg0) {
		return this.event.startDateAndTime.compareTo(arg0.event.startDateAndTime);
	}
}