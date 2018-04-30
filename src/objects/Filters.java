package objects;

import JCode.fileHelper;

public class Filters {

    private String sortBy, ascDesc;
    private boolean solved,
            unsolved,
            locked,
            unlocked;
    private static fileHelper fHelper;

    public Filters() {
        fHelper = new fileHelper();
    }

    @Override
    public String toString() {

        String filters = " 1 ";

        if (solved) {
            filters = filters + " AND ESOLV = 'S' ";
        } else if (unsolved) {
            filters = filters + " AND ESOLV = 'N' ";
        } else if (locked) {
            filters = filters + " AND LOCKD != 0 ";
        } else if (unlocked) {
            filters = filters + " AND LOCKD = 0 ";
        }

        filters = filters + " ORDER BY " + sortBy + " " + ascDesc;

        return filters;
    }

    public void writeToFile() {
        String filter = "";
        filter = sortBy + "," + ascDesc + "," + solved + "," + unsolved + "," + locked + "," + unlocked;
        fHelper.WriteFilter(filter);
    }

    public static Filters readFromFile() {
        String filters[] = fHelper.ReadFilter().split(",");
        Filters filter = new Filters();
        filter.setSortBy(filters[0]);
        filter.setAscDesc(filters[1]);

        filter.setSolved(Boolean.parseBoolean(filters[2]));
        unsolved = Boolean.parseBoolean(filters[3]);
        locked = Boolean.parseBoolean(filters[4]);
        unlocked = Boolean.parseBoolean(filters[5]);
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {

        switch (sortBy) {
            case "Tickets":
                this.sortBy = " EMNO ";
                break;
            case "From":
                this.sortBy = " FRADD ";
                break;
            case "Subject":
                this.sortBy = " SBJCT ";
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
}
