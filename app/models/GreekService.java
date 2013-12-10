package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.annotation.Sql;

@Entity
@Sql
public class GreekService extends Model {
	public int greek_id;
	public int numberOfServiceHours;
	
}

