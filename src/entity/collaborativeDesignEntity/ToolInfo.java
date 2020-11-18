package entity.collaborativeDesignEntity;

/**
 * Created by winter on 2014/11/28.
 */
public class ToolInfo {
    private String taskId;//这里的taskId存的是树状节点
    private String solutionId;
    private String toolType;
    private String nodeId;
    private String nodeFilePath;

    public ToolInfo(String taskId,String solutionId,String toolType,String nodeId,String nodeFilePath){
        this.taskId = taskId;
        this.solutionId = solutionId;
        this.toolType = toolType;
        this.nodeId = nodeId;
        this.nodeFilePath = nodeFilePath;
    }
    public String getSolutionId() {
        return solutionId;
    }

    public void setSolutionId(String solutionId) {
        this.solutionId = solutionId;
    }

    public String getToolType() {
        return toolType;
    }

    public void setToolType(String toolType) {
        this.toolType = toolType;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeFilePath() {
        return nodeFilePath;
    }

    public void setNodeFilePath(String nodeFilePath) {
        this.nodeFilePath = nodeFilePath;
    }
}
