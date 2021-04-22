package JCode;

import JCode.mysql.mySqlConn;
import objects.EmailOld;
import objects.EventOld;
import objects.TaskOld;

import java.util.List;

public class NotifyActivities {

    private mySqlConn sql;
    private trayHelper tray;
    private emailControl email;

    private final static String TASK_CAPTION = "Task Alert";
    private final static String EVENT_CAPTION = "Event Alert";

    public NotifyActivities() {
//        sql = new mySqlConn();
        tray = new trayHelper();
        email = new emailControl();
    }

    public void startNotifying() {
        try {
            Thread.sleep(1000 * 10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String currentDate = CommonTasks.getCurrentTimeStamp().split("\\s+")[0];
        //1- Notify tasks
        notifyTasks(currentDate);
        //2- Notify Events
        notifyEvents(currentDate);
    }

    private void notifyTasks(String currentDate) {
        //Get only todays task and ones that havent already been notified
        List<TaskOld> openList = sql.getAllTasks(" AND TS_DDATE = '" + currentDate + "' AND NOTIFIED = 0 ");
        for (TaskOld taskOld : openList) {
            //1- Alert on desktop
            String body = "Subject: " + taskOld.getSubject() + "\n";
            sendDesktopNotification(TASK_CAPTION, body);
            //2- Alert on Email
            body = body + "Description: " + taskOld.getDesc() + "\n" +
                    "Created By: " + taskOld.getCreatedBy() + "\n" +
                    "Created On: " + taskOld.getCreatedOn() + "\n";
            sendEmailNotification(TASK_CAPTION, body, taskOld.getCreatedByCode());
            //3- Mark task as notified
            sql.markNotified(taskOld);
        }
    }

    private void notifyEvents(String currentDate) {
        //Get only todays events and ones that havent already been notified
        List<EventOld> openList = sql.getAllEvents(" AND ES_FROM >= '" + currentDate + "' AND '" + currentDate + "' <= ES_TO AND NOTIFIED = 0");
        for (EventOld eventOld : openList) {
                //1- Alert on desktop
                String body = "Title: " + eventOld.getTitle() + "\n";
                sendDesktopNotification(EVENT_CAPTION, body);
                //2- Alert on Email
                body = body + "Description: " + eventOld.getDesc() + "\n" +
                        "Created By: " + eventOld.getCreatedBy() + "\n" +
                        "Created On: " + eventOld.getCreatedOn() + "\n";
                sendEmailNotification(EVENT_CAPTION, body, eventOld.getCreatedByCode());
                //3- Mark task as notified
                sql.markNotified(eventOld);
        }
    }

    private void sendDesktopNotification(String caption, String body) {
        tray.displayNotification(caption, body);
    }

    private void sendEmailNotification(String subject, String body, int createdBy) {
        String toAddress = sql.getUserDetails(createdBy).getEmaill();
        EmailOld e = new EmailOld();
        e.setSubject(subject);
        e.setBody(body);
    }


}
