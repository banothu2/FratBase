package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.annotation.Sql;

@Entity
@Sql
public class GreekRank extends Model implements Comparable<GreekRank> {
	public int greek_id;
	public String name; 
	public String university; 
	public int rank;
	public int commonFriendCount;
	public int numberOfEvents;
	public int numberOfServiceHours;

	public int index;

	public int compareTo(GreekRank r) {
		return r.rank < rank ? -1 : 1;
	}
}

