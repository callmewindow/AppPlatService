package entity;

/**
 * Created by winter on 2015/4/22.
 */
public class OrbitSection {
    private  int numberId;
    private String currentPlanet;
    private String targetPlanet;
    private String startTime;
    private String endTime;
    private double speedDifference;

    public int getNumberId() {
        return numberId;
    }

    public void setNumberId(int numberId) {
        this.numberId = numberId;
    }

    public double getSpeedDifference() {
        return speedDifference;
    }

    public void setSpeedDifference(double speedDifference) {
        this.speedDifference = speedDifference;
    }



    public String getCurrentPlanet() {
        return currentPlanet;
    }

    public void setCurrentPlanet(String currentPlanet) {
        this.currentPlanet = currentPlanet;
    }

    public String getTargetPlanet() {
        return targetPlanet;
    }

    public void setTargetPlanet(String targetPlanet) {
        this.targetPlanet = targetPlanet;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }


}
