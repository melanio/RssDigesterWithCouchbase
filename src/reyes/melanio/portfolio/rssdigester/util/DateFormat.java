package reyes.melanio.portfolio.rssdigester.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormat {
	private SimpleDateFormat simpleDateFormat;

	DateFormat(String format) {
		simpleDateFormat = new SimpleDateFormat(format, Locale.ENGLISH);
	}

	public Date parse(String dateString) throws ParseException {
		return simpleDateFormat.parse(dateString);
	}
}

