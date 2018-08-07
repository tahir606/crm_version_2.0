package objects;

public class NotificationSettings {

    private boolean notifyTaskDueDate, notifyEventFromDate;

    public NotificationSettings() {
    }

    public NotificationSettings(boolean notifyTaskDueDate, boolean notifyEventFromDate) {
        this.notifyTaskDueDate = notifyTaskDueDate;
        this.notifyEventFromDate = notifyEventFromDate;
    }

    public boolean isNotifyTaskDueDate() {
        return notifyTaskDueDate;
    }

    public void setNotifyTaskDueDate(boolean notifyTaskDueDate) {
        this.notifyTaskDueDate = notifyTaskDueDate;
    }

    public boolean isNotifyEventFromDate() {
        return notifyEventFromDate;
    }

    public void setNotifyEventFromDate(boolean notifyEventFromDate) {
        this.notifyEventFromDate = notifyEventFromDate;
    }
}
