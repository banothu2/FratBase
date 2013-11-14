package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

@Entity
public class User extends Model {
	@Id
	public int id;
	public String name;
	public String username;
	public String email;
	public String passwordHash;
	public String university;
	public String greek;
	
	public static Finder<Long, User> find = new Finder<Long, User>(Long.class, User.class);
	
	public static String hash(String data) {
		return data; // TODO: Add hash function
	}
}
