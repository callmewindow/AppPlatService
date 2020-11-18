package entity;

import java.util.List;

/**
 * Created by winter on 2014/10/14.
 */
public class ColTaskInfo {
    private String taskId;
    private String taskName;
    private String startTime;
    private String creatorName;
    private int menberNumber;
    private String recentlyModified;
    private String orbitDesign;
    private String structDesign;
    private String coverAnalysis;
    private String dataTransAnalysis;

    public String getDataTransAnalysis() {
        return dataTransAnalysis;
    }

    public void setDataTransAnalysis(String dataTransAnalysis) {
        this.dataTransAnalysis = dataTransAnalysis;
    }

    public String getCoverAnalysis() {
        return coverAnalysis;
    }

    public void setCoverAnalysis(String coverAnalysis) {
        this.coverAnalysis = coverAnalysis;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public int getMenberNumber() {
        return menberNumber;
    }

    public void setMenberNumber(int menberNumber) {
        this.menberNumber = menberNumber;
    }

    public String getOrbitDesign() {
        return orbitDesign;
    }

    public void setOrbitDesign(String orbitDesign) {
        this.orbitDesign = orbitDesign;
    }

    public String getStructDesign() {
        return structDesign;
    }

    public void setStructDesign(String structDesign) {
        this.structDesign = structDesign;
    }

    public String getRecentlyModified() {
        return recentlyModified;
    }

    public void setRecentlyModified(String recentlyModified) {
        this.recentlyModified = recentlyModified;
    }
}
