package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

@Entity
public class Greek extends Model {
	@Id
	public int id;
	public String name;
	public String university;
	
	public static Finder<Long, Greek> find = new Finder<Long, Greek>(Long.class, Greek.class);

}
