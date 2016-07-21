package it.bonavita.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ASDataFornat {
	
	public static String dateToString(Date date) {
        if (date != null) {
            DateFormat myDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm", Locale.getDefault());
            String str = myDateFormat.format(date);
            return (str != null) ? str : "";
        } 
        else {
            return "";
        }
    }
}
