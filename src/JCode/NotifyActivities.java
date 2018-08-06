package JCode;

import JCode.mysql.mySqlConn;
import objects.Email;
import objects.Task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NotifyActivities {

    private mySqlConn sql;
    private trayHelper tray;
    private emailControl email;

    private final static String TASK_CAPTION = "Task Alert";
    private final static String EVENT_CAPTION = "Event Alert";

    public NotifyActivities() {
        sql = new mySqlConn();
        tray = new trayHelper();
        email = new emailControl();


    }

    public void startNotifying() {
        //1- Notify tasks
        notifyTasks();
        //2- Notify Events
        notifyEvents();
    }

    private void notifyTasks() {
        //Get only todays task
        String currentDate = CommonTasks.getCurrentTimeStamp().split("\\s+")[0];
        List<Task> openTasks = sql.getAllTasks(" AND TS_DDATE = '" + currentDate.split("\\s+")[0] +  "' ");
        for (Task task: openTasks) {
            if (currentDate.equals(task.getDueDate().split("\\s+")[0])) {       //Check if due date is same as today's date
                //1- Alert on desktop
                String body = "Subject: " + task.getSubject() + "\n";
                sendDesktopNotification(TASK_CAPTION, body);
                //2- Alert on Email
                body = body + "Description: " + task.getDesc() + "\n" +
                        "Created By: " + task.getCreatedBy() + "\n" +
                        "Created On: " + task.getCreatedOn() + "\n";
                sendEmailNotification(TASK_CAPTION, body);
            } else
                continue;
        }
    }

    private void notifyEvents() {

    }

    private void sendDesktopNotification(String caption, String body) {
        tray.displayNotification(caption, body);
    }

    private void sendEmailNotification(String subject, String body) {
        Email e = new Email();
        e.setSubject(subject);
        e.setBody(body);
        email.sendEmail(e, null);
    }


}
