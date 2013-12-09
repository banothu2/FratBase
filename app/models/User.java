package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import models.ServiceLog;

@Entity
public class User extends Model {
	@Id
	public int id;
	public String firstName;
	public String lastName;
	public String username;
	public String passwordHash;
	public String email;
	public String university;
	public String greekOrganization;
	
	public int age; 
	public String sex;
	public String graduationDate; 
	public String major;
	public String phoneNumber; 
	public String linkedIn;
	public boolean relationshipStatus; 
	
	public String greekName;
	public String profilePicture; 
	public String facebookId;
	public String resume; 
	public String status; 
	public int accessLevel;
	
	public static Finder<Long, User> find = new Finder<Long, User>(Long.class, User.class);
	
	public static String hash(String data) {
		return data; // TODO: Add hash function
	}
}
