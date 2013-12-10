package models;

import java.util.*;

import javax.persistence.*;

import com.avaje.ebean.annotation.Sql;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;
import  play.data.format.Formats;


import models.ServiceLog;

public class AEvent {
	public Event event;
	public boolean attending;
}
