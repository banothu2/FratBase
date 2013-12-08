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
	public String userId;
	public String university;
	public String greekOrganization;
	public String serviceType;
	public String date;
	public String hours;
	public String minutes;
	public String comments;
	
	public static Finder<Long, ServiceLog> find = new Finder<Long, ServiceLog>(Long.class, ServiceLog.class);
	
	public static String hash(String data) {
		return data; // TODO: Add hash function
	}
}
