
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Operations {
	public Operations() {

	}
	
	public static String convert24To12(String time) {
		try {
			//System.out.println(Integer.valueOf(time.substring(0, 2)));
			//
		    final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
		    final Date dateObj = sdf.parse(time);
		    return new SimpleDateFormat("KK:mm").format(dateObj);
		} catch (final ParseException e) {
		    e.printStackTrace();
		    return "";
		}
	}
}