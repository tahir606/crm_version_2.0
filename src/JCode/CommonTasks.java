package JCode;

import Email.EResponse.EResponseController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class CommonTasks {

    public CommonTasks() {
    }

    public static String getCurrentTimeStamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getTimeFormatted(String timeStamp) {
        // Note, MM is months, not mm
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        DateFormat outputFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm a");

        Date date = null;
        try {
            date = inputFormat.parse(timeStamp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outputText = outputFormat.format(date);
        return outputText;
    }

    public static String getDateFormatted(String timeStamp) {
        // Note, MM is months, not mm
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        DateFormat outputFormat = new SimpleDateFormat("dd-MMM-yyyy");

        Date date = null;
        try {
            date = inputFormat.parse(timeStamp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outputText = outputFormat.format(date);
        return outputText;
    }

    public static String getDataFormattedOnlyData(String timeStamp) {
        // Note, MM is months, not mm
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat outputFormat = new SimpleDateFormat("dd-MMM-yyyy");

        Date date = null;
        try {
            date = inputFormat.parse(timeStamp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outputText = outputFormat.format(date);
        return outputText;
    }

    public static String getAge(String timeStamp) {

        // Note, MM is months, not mm
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");

        Date date = null;
        try {
            date = inputFormat.parse(timeStamp);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            return "";
        }

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        //Add one to month {0 - 11}
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        LocalDate birthdate = LocalDate.of(year, month, day);
        LocalDate now = LocalDate.now();

        return String.valueOf(Period.between(birthdate, now).getYears());

    }

    public static LocalDate createLocalDate(String timeStamp) {

        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");

        Date date = null;
        try {
            date = inputFormat.parse(timeStamp);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        //Add one to month {0 - 11}
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        LocalDate localDate = LocalDate.of(year, month, day);

        return localDate;
    }

}
