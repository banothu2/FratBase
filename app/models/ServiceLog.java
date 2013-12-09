package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

@Entity
public class ServiceLog extends Model {
	@Id
	public int id;
	@ManyToOne
	public User user;
	@ManyToOne 
	public Greek greek;
	public String serviceType;
	public String date;
	public int hours;
	public int minutes;
	public String comments;
	
	
	public static Finder<Long, ServiceLog> find = new Finder<Long, ServiceLog>(Long.class, ServiceLog.class);

}
