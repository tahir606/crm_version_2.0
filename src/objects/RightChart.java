package objects;

public class RightChart {
    private int rightsChartId,userCode,rightsCode;
RightsList rightsList;

    public RightChart() {
    }

    public int getRightsChartId() {
        return rightsChartId;
    }

    public void setRightsChartId(int rightsChartId) {
        this.rightsChartId = rightsChartId;
    }

    public int getUserCode() {
        return userCode;
    }

    public void setUserCode(int userCode) {
        this.userCode = userCode;
    }

    public int getRightsCode() {
        return rightsCode;
    }

    public void setRightsCode(int rightsCode) {
        this.rightsCode = rightsCode;
    }

    public RightsList getRightsList() {
        return rightsList;
    }

    public void setRightsList(RightsList rightsList) {
        this.rightsList = rightsList;
    }

    @Override
    public String toString() {
        return "RightChart{" +
                "rightsChartId=" + rightsChartId +
                ", userCode=" + userCode +
                ", rightsCode=" + rightsCode +
                ", rightsLists=" + rightsList +
                '}';
    }
}
