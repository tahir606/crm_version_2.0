package objects;

import JCode.FileHelper;

public class Filters {

    public static String sortBy, ascDesc;
    private boolean solved,
            unsolved,
            locked,
            unlocked,
            lockedByMe,
            hideReminders,
            archived,
            isResolve;
    private static FileHelper fHelper;

    public Filters() {
        fHelper = new FileHelper();
    }

    @Override
    public String toString() {

        String filters = "";

        if (solved) {
            filters = filters + "," + "solved";
        }
        if (unsolved) {
            filters = filters + "," + "unsolved";
        }
        if (locked) {
            filters = filters + "," + "locked";
        }
        if (unlocked) {
            filters = filters + "," + "unlocked";
        }

        if (lockedByMe) {
            filters = filters + "," + "lockedByMe";
        }

        if (hideReminders) {
            filters = filters + "," + "subject";
        }

        if (archived) {
            filters = filters + "," + "freeze";
        } else {
            filters = filters + "," + "unfreeze";
        }
        if (isResolve) {
            filters = filters + "," + "resolve";
        }


        return filters;
    }

    public void writeToFile() {
        String filter;
        filter = getSortBy() + "," + ascDesc + "," + solved + "," + unsolved + "," + locked + "," + unlocked + "," + lockedByMe + "," + hideReminders + "," + archived+ "," + isResolve;
        fHelper.WriteFilter(filter);
    }

    public static Filters readFromFile() {
        if (fHelper == null) {
            fHelper = new FileHelper();
        }
        String[] filters;
        try {
            filters = fHelper.ReadFilter().split(",");
            Filters filter = new Filters();
            filter.setSortBy(filters[0]);//tickets
            filter.setAscDesc(filters[1]);//desc

            filter.setSolved(Boolean.parseBoolean(filters[2]));//false
            filter.setUnsolved(Boolean.parseBoolean(filters[3]));//true
            filter.setLocked(Boolean.parseBoolean(filters[4]));//false
            filter.setUnlocked(Boolean.parseBoolean(filters[5]));//false
            filter.setLockedByMe(Boolean.parseBoolean(filters[6]));//false
            filter.setHideReminders(Boolean.parseBoolean(filters[7]));//false
            filter.setArchived(Boolean.parseBoolean(filters[8]));//false
            filter.setResolve(Boolean.parseBoolean(filters[9]));
            return filter;
        } catch (Exception e) {
            e.printStackTrace();
            Filters ft = new Filters();
            ft.setSortBy("Tickets");
            ft.setAscDesc("Desc");
            ft.setSolved(false);
            ft.setUnsolved(false);
            ft.setLocked(false);
            ft.setUnlocked(false);
            ft.setArchived(false);
            ft.setResolve(false);
            return ft;
        }
    }

    public String getSortBy() {
        try {
            switch (sortBy) {
                case "EMNO":
                    this.sortBy = "Tickets";
                    break;
                case "FRADD":
                    this.sortBy = "From";
                    break;
                case "SBJCT":
                    this.sortBy = "Subject";
                    break;
            }
            return sortBy;
        } catch (NullPointerException e) {
            return "";
        }
    }

    public void setSortBy(String sortBy) {
        switch (sortBy) {
            case "Tickets":
                this.sortBy = "EMNO";
                break;
            case "From":
                this.sortBy = "FRADD";
                break;
            case "Subject":
                this.sortBy = "SBJCT";
                break;
        }
    }

    public boolean isResolve() {
        return isResolve;
    }

    public void setResolve(boolean resolve) {
        isResolve = resolve;
    }

    public String getAscDesc() {
        return ascDesc;
    }

    public void setAscDesc(String ascDesc) {
        this.ascDesc = ascDesc;
    }

    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    public boolean isUnsolved() {
        return unsolved;
    }

    public void setUnsolved(boolean unsolved) {
        this.unsolved = unsolved;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }

    public boolean isLockedByMe() {
        return lockedByMe;
    }

    public void setLockedByMe(boolean lockedByMe) {
        this.lockedByMe = lockedByMe;
    }

    public boolean isHideReminders() {
        return hideReminders;
    }

    public void setHideReminders(boolean hideReminders) {
        this.hideReminders = hideReminders;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }
}
