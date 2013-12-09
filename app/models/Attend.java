package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

@Entity
public class Attend extends Model {
	@Id
	public int id;
	@ManyToOne
	public User user;
	@ManyToOne
	public Event event;
	
	public static Finder<Long, Attend> find = new Finder<Long, Attend>(Long.class, Attend.class);
	
}
