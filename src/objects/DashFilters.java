package objects;

public class DashFilters {
    String from,to,filterText;
    int panel;

    public DashFilters() {
    }

    public DashFilters(String from, String to, int panel) {
        this.from = from;
        this.to = to;
        this.panel = panel;
    }
    public DashFilters(String from, String to,String filterText, int panel) {
        this.from = from;
        this.to = to;
        this.panel = panel;
        this.filterText = filterText;
    }
    public DashFilters(String filterText, int panel) {
        this.filterText = filterText;
        this.panel = panel;
    }

    @Override
    public String toString() {
        return "DashFilters{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", filterText='" + filterText + '\'' +
                ", panel=" + panel +
                '}';
    }

    public String getFilterText() {
        return filterText;
    }

    public void setFilterText(String filterText) {
        this.filterText = filterText;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public int getPanel() {
        return panel;
    }

    public void setPanel(int panel) {
        this.panel = panel;
    }
}
