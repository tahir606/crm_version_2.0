package objects;

import JCode.fileHelper;

public class Filters {

    private String sortBy, ascDesc;
    private boolean solved,
            unsolved,
            locked,
            unlocked,
            archived;
    private static fileHelper fHelper;

    public Filters() {
        fHelper = new fileHelper();
    }

    @Override
    public String toString() {

        String filters = " 1 ";

        if (solved) {
            filters = filters + " AND ESOLV = 'S' ";
        }
        if (unsolved) {
            filters = filters + " AND ESOLV = 'N' ";
        }
        if (locked) {
            filters = filters + " AND LOCKD != 0 ";
        }
        if (unlocked) {
            filters = filters + " AND LOCKD = 0 ";
        }
        if (archived) {
            filters = filters + " AND FREZE = 1 ";
        } else {
            filters = filters + " AND FREZE = 0 ";
        }

        filters = filters + " ORDER BY " + sortBy + " " + ascDesc;

        return filters;
    }

    public void writeToFile() {
        String filter;
        filter = getSortBy() + "," + ascDesc + "," + solved + "," + unsolved + "," + locked + "," + unlocked + "," + archived;
        fHelper.WriteFilter(filter);
    }

    public static Filters readFromFile() {
        if (fHelper == null) {
            fHelper = new fileHelper();
        }
        String[] filters;
        try {
            filters = fHelper.ReadFilter().split(",");

            Filters filter = new Filters();
            filter.setSortBy(filters[0]);
            filter.setAscDesc(filters[1]);

            filter.setSolved(Boolean.parseBoolean(filters[2]));
            filter.setUnsolved(Boolean.parseBoolean(filters[3]));
            filter.setLocked(Boolean.parseBoolean(filters[4]));
            filter.setUnlocked(Boolean.parseBoolean(filters[5]));
            filter.setArchived(Boolean.parseBoolean(filters[6]));

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

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }
}
