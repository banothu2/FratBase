package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import models.ServiceLog;

@Entity
public class Event extends Model {

	@Id
	public int id;
	@ManyToOne
	public Greek greek;
	public String name;
	public Date startDateAndTime;
	public Date endDateAndTime;
	public boolean openEvent;
	public String location;
	public String type;

	
	public static Finder<Long, Event> find = new Finder<Long, Event>(Long.class, Event.class);
	
}
