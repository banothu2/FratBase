package viewmodels;
import  java.util.Date;
import  play.data.format.Formats;

public class EventModel {

	public String name;
	@Formats.DateTime(pattern="dd/MM/yyyy HH:mm")
	public Date startDateAndTime;
	@Formats.DateTime(pattern="dd/MM/yyyy HH:mm")
	public Date endDateAndTime;
	public boolean openEvent;
	public String location;
	public String eventType;
}
