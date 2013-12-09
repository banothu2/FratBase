package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

@Entity
public class Address extends Model {
	@Id
	public int id;
	@ManyToOne
	public User user;
	public String addressLineOne; 
	public String addressLineTwo;
	public String city;
	public String state;
	public String zip; 
	public String country;
	public String addressType;
	
	public static Finder<Long, Address> find = new Finder<Long, Address>(Long.class, Address.class);
	
}
