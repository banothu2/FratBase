package viewmodels;
import  java.util.Date;
import  play.data.format.Formats;

public class SuggestEventModel {
	@Formats.DateTime(pattern="dd/MM/yyyy HH:mm")
	public Date startDateAndTime;
	@Formats.DateTime(pattern="dd/MM/yyyy HH:mm")
	public Date endDateAndTime;
}
