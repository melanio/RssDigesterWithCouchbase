package reyes.melanio.portfolio.rssdigester.util;

import java.text.ParseException;
import java.util.Date;

public class DateParser {
	private static DateFormat[] dateFormats;

	protected DateParser() {

	}

	public Date parse(String dateString) {
		Date result = null;
		for (DateFormat dateformat : dateFormats) {
			try {
				result = dateformat.parse(dateString.trim());
			} catch (ParseException e) {
			}
		}
		return result;
	}

	public void setDateFormats(DateFormat[] dateFormats) {
		DateParser.dateFormats = dateFormats;
	}
}

